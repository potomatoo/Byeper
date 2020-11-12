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
            Lorem ipsum dolor sit amet, semper quis, sapien id natoque elit.
            Nostra urna at, magna at neque sed sed ante imperdiet, dolor mauris
            cursus velit, velit non, sem nec. Volutpat sem ridiculus placerat
            leo, augue in, duis erat proin condimentum in a eget, sed fermentum
            sed vestibulum varius ac, vestibulum volutpat orci ut elit eget
            tortor. Ultrices nascetur nulla gravida ante arcu. Pharetra rhoncus
            morbi ipsum, nunc tempor debitis, ipsum pellentesque, vitae id quam
            ut mauris dui tempor, aptent non. Quisque turpis. Phasellus quis
            lectus luctus orci eget rhoncus. Amet donec vestibulum mattis
            commodo, nulla aliquet, nibh praesent, elementum nulla. Sit lacus
            pharetra tempus magna neque pellentesque, nulla vel erat. Justo ex
            quisque nulla accusamus venenatis, sed quis. Nibh phasellus gravida
            metus in, fusce aenean ut erat commodo eros. Ut turpis, dui integer,
            nonummy pede placeat nec in sit leo. Faucibus porttitor illo taciti
            odio, amet viverra scelerisque quis quis et tortor, curabitur morbi
            a. Enim tempor at, rutrum elit condimentum, amet rutrum vitae tempor
            torquent nunc. Praesent vestibulum integer maxime felis. Neque
            aenean quia vitae nostra, tempus elit enim id dui, at egestas
            pulvinar.
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="dialog = false">
              취소
            </v-btn>
            <v-btn color="green darken-1" text @click="dialog = false">
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
import { Component, Vue, Prop } from "vue-property-decorator";
import h2c from "html2canvas";
import { jsPDF } from "jspdf";
import { namespace } from "vuex-class";

const LecturesModule = namespace("LecturesModule");

@Component
export default class ImageCaptureChip extends Vue {
  @Prop(Object) readonly player: any;
  @Prop(String) readonly videoId!: string;
  @LecturesModule.Action FETCH_CAPTURE_IMAGE: any;

  ticksLabels = ["하", "중", "상"];

  dialog = false;
  $vuetify: any;

  addCapture(img: string) {
    this.$emit("addCapture", img);
  }
  async capture() {
    h2c(document.querySelector(".editor__content") as HTMLElement)
      .then(canvas => this.addCapture(canvas.toDataURL("image/jpeg")))
      .catch(err => console.error(err));
  }
  async imageCapture() {
    // this.capture();
    const getVideoTime = await this.player.getCurrentTime();
    this.FETCH_CAPTURE_IMAGE({ id: this.videoId, time: getVideoTime });
    console.log(getVideoTime);
  }
  imageCaptureAll() {
    console.log("all");
  }
  async toPdf() {
    await this.$vuetify.goTo(0);
    h2c((await document.querySelector(".ProseMirror")) as HTMLElement).then(
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
</style>
