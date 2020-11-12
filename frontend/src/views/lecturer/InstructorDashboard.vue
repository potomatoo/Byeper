<template>
  <div>
    <v-row style="margin: 10px 10%" cols="12" sm="6" offset-sm="3">
      <h2 class="mt-12">나의 강좌</h2>
      <vue-slick-carousel v-if="Lectures" class="slick mt-12" v-bind="settings">
        <div v-for="lecture in Lectures" :key="lecture.playlistId">
          <img
            class="Lecture-img"
            :src="lecture.playlistImg"
            :alt="lecture.playlistTitle"
            @click="selectLecture(lecture.playlistId)"
          />
        </div>
      </vue-slick-carousel>
    </v-row>
    <v-row style="margin: 10px 10%">
      <v-col cols="5">
        <v-card
          v-if="SelectedPlayLists !== []"
          class="mt-12"
          style="height: 50vh; overflow-y: scroll; overflow-x: hidden"
        >
          <h2 class="ml-3">강의 목록</h2>
          <v-list
            three-line
            v-for="(Lecture, index) in SelectedPlayLists"
            :key="index"
          >
            <v-list-item v-for="(video, idx) in Lecture.videos" :key="idx">
              <v-list-item-avatar tile>
                <v-img :src="video.video_img"></v-img>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title
                  v-html="video.video_title"
                ></v-list-item-title>
                <v-list-item-subtitle
                  v-html="video.video_description"
                ></v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
      <v-col cols="7">
        <v-card class="mt-12">
          <h2 class="ml-3">수강 통계</h2>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { Axios } from "@/service/axios.service";

// import { namespace } from "vuex-class";
// carousel 관련 import
import VueSlickCarousel from "vue-slick-carousel";
import "vue-slick-carousel/dist/vue-slick-carousel.css";
import "vue-slick-carousel/dist/vue-slick-carousel-theme.css";

// const InstructorModule = namespace("InstructorModule");

@Component({
  components: {
    VueSlickCarousel
  }
})
export default class InstructorDashboard extends Vue {
  LecturePlayLists = [];
  SelectedPlayLists = [];
  Lectures = [
    {
      LectureName: "파이썬 좋아",
      LectureId: "1",
      LectureThumb:
        "https://images.unsplash.com/photo-1502224562085-639556652f33?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
      LectureDesc: "파이썬 12만원 타세요"
    },
    {
      LectureName: "파이썬 좋아",
      LectureId: "2",
      LectureThumb:
        "https://images.unsplash.com/photo-1502224562085-639556652f33?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
      LectureDesc: "파이썬 12만원 타세요"
    },
    {
      LectureName: "파이썬 좋아",
      LectureId: "3",
      LectureThumb:
        "https://images.unsplash.com/photo-1502224562085-639556652f33?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
      LectureDesc: "파이썬 12만원 타세요"
    },
    {
      LectureName: "파이썬 좋아",
      LectureId: "4",
      LectureThumb:
        "https://images.unsplash.com/photo-1502224562085-639556652f33?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
      LectureDesc: "파이썬 12만원 타세요"
    },
    {
      LectureName: "파이썬 좋아",
      LectureId: "5",
      LectureThumb:
        "https://images.unsplash.com/photo-1502224562085-639556652f33?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
      LectureDesc: "파이썬 12만원 타세요"
    },
    {
      LectureName: "파이썬 좋아",
      LectureId: "6",
      LectureThumb:
        "https://images.unsplash.com/photo-1502224562085-639556652f33?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
      LectureDesc: "파이썬 12만원 타세요"
    }
  ];
  settings = {
    arrows: true,
    dots: false,
    infinite: true,
    slidesToShow: 5,
    slidesToScroll: 1,
    autoplay: true,
    speed: 2000,
    autoplaySpeed: 3000,
    responsive: [
      {
        breakpoint: 1399,
        settings: {
          slidesToShow: 4
        }
      },
      {
        breakpoint: 1099,
        settings: {
          slidesToShow: 3
        }
      },
      {
        breakpoint: 799,
        settings: {
          slidesToShow: 2
        }
      },
      {
        breakpoint: 499,
        settings: {
          slidesToShow: 2
        }
      }
    ]
  };

  async getTotalLecture() {
    const Lectures = await this.getMyPlayList();

    const getLectureInfo = async playlistId => {
      const LectureInfo = await Axios.instance.get(
        "/api/public/video/findplaylist",
        {
          params: {
            playlistId: playlistId
          }
        }
      );
      return { videos: LectureInfo.data.data, playlistId };
    };

    const getLecture = async Lectures => {
      const req = Lectures.map(Lecture => {
        return getLectureInfo(Lecture.playlistId).then(res => {
          return res;
        });
      });
      return Promise.all(req);
    };

    getLecture(Lectures).then(res => {
      this.LecturePlayLists = res;
      this.SelectedPlayLists.push(this.LecturePlayLists[0]);
      console.log("초기상태", this.SelectedPlayLists);
    });
  }

  async getMyPlayList() {
    try {
      const myLectures = await Axios.instance.get("/api/public/playlist/user");
      if (myLectures) this.Lectures = myLectures.data.data;
      return myLectures.data.data;
    } catch (e) {
      console.error(e);
    }
  }

  selectLecture(playlistId) {
    this.SelectedPlayLists = this.LecturePlayLists.filter(
      playlist => playlist.playlistId === playlistId
    );
    console.log("나중상태", this.SelectedPlayLists);
  }

  async created() {
    await this.getTotalLecture();
  }
}
</script>

<style scoped>
.slick {
  width: 100%;
  height: 165px;
}
.Lecture-img {
  margin: 5px;
  width: 94%;
  height: 165px;
  transition: all ease 1ms;
}

.Lecture-img:hover {
  cursor: pointer;
  box-shadow: 0px 0px 10px 5px;
  transform: scale(1.1);
}

.router-link {
  text-decoration: none;
  color: inherit;
  border: 0;
  outline: none;
}
</style>
