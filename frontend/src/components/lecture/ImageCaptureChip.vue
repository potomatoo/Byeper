<template>
  <v-row justify="center" align="center">
    <v-chip class="chip chip-btn" color="indigo darken-3" outlined x-large>
      <v-btn class="mx-2" fab icon large color="teal" @click="imageCapture">
        <v-icon>mdi-camera-outline</v-icon>
      </v-btn>
      <v-dialog v-model="dialog" width="600px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn fab color="red" v-bind="attrs" v-on="on">
            <v-icon>mdi-image-multiple-outline</v-icon>
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span>사진 캡쳐 민감도 설정</span>
          </v-card-title>
          <v-slider
            class="capture-slider"
            v-model="sensitivity"
            :tick-labels="ticksLabels"
            :max="2"
            color="#E53935"
            track-color="#EF9A9A"
            step="1"
            ticks="always"
            tick-size="4"
          ></v-slider>
          <v-divider class="slider-divide"></v-divider>
          <v-card-text>
            <div v-for="img in selectedCaptures" :key="img.address">
              <h1>{{ img.address }}</h1>
              <img
                :src="`http://k3b108.p.ssafy.io${img.address}`"
                class="all-capture-img"
              />
            </div>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="dialog = false">
              취소
            </v-btn>
            <v-btn color="green darken-1" text @click="imageCaptureAll">
              적용
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-btn class="mx-2" fab icon large color="teal" @click="toPdf">
        <v-icon>mdi-file-pdf-outline</v-icon>
      </v-btn>
    </v-chip>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue, Prop, Watch } from "vue-property-decorator";
import h2c from "html2canvas";
import { jsPDF } from "jspdf";
import { namespace } from "vuex-class";
import { CaptureImages, Lecture } from "../../store/Lectures.interface";

const LecturesModule = namespace("LecturesModule");

@Component
export default class ImageCaptureChip extends Vue {
  @Prop(Object) readonly player: any;
  @Prop(String) readonly videoId!: string;
  @LecturesModule.State lecture!: Lecture;
  @LecturesModule.Action FETCH_CAPTURE_IMAGE: any;
  @LecturesModule.State allCaptureImgs!: CaptureImages[];

  ticksLabels = ["하", "중", "상"];
  sensitivity = 2;
  selectedCaptures: CaptureImages[] = [];

  dialog = false;
  $vuetify: any;

  addCapture({ url, time }: { url: string; time: number }) {
    this.$emit("addCapture", { url, time });
  }
  async imageCapture() {
    const getVideoTime = await this.player.getCurrentTime();
    const videoUrl = await this.FETCH_CAPTURE_IMAGE({
      id: this.lecture.video_id,
      time: getVideoTime
    });
    this.addCapture({ url: videoUrl, time: getVideoTime });
  }
  imageCaptureAll() {
    this.selectedCaptures.forEach(img =>
      this.$emit("addCapture", { url: img.address, time: img.time })
    );
    this.dialog = false;
  }
  async toPdf() {
    await this.$vuetify.goTo(0);
    h2c((await document.querySelector(".editor__content")) as HTMLElement).then(
      canvas => {
        const imgData = canvas.toDataURL("image/jpeg");
        const margin = 10;
        const imgWidth = 210 - 2 * margin;
        const pageHeight = 295;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;
        let heightLeft = imgHeight;
        const doc = new jsPDF("p", "mm", "a4");
        let position = 5;

        doc.addImage(imgData, "PNG", margin, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;
        while (heightLeft >= 20) {
          position = heightLeft - imgHeight;
          doc.addPage();
          doc.addImage(imgData, "PNG", margin, position, imgWidth, imgHeight);
          heightLeft -= pageHeight;
        }
        doc.save("저장.pdf");
      }
    );
  }
  @Watch("allCaptureImgs", { immediate: true })
  setAllImgs() {
    this.selectedCaptures = this.allCaptureImgs;
  }
  @Watch("sensitivity")
  imgsFilter() {
    if (this.sensitivity === 0) {
      this.selectedCaptures = this.allCaptureImgs.filter(
        img => img.diff >= 10000
      );
    } else if (this.sensitivity === 1) {
      this.selectedCaptures = this.allCaptureImgs.filter(
        img => img.diff >= 6000
      );
    } else {
      this.selectedCaptures = this.allCaptureImgs;
    }
  }
}
</script>

<style scoped>
.v-chip:before {
  background-color: inherit !important;
}
.chip-btn {
  width: 242px;
  position: fixed;
  bottom: 10px;
  margin: 0 auto;
}
.capture-slider {
  padding: 16px 24px;
}
.slider-divide {
  width: 70%;
  background-color: white;
  margin: 12px auto 30px;
}
.v-slider__tick-label {
  color: #ef9a9a !important;
}
.all-capture-img {
  width: 100%;
}
</style>
