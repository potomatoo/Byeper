<template>
  <div>
    <v-row
      style="margin: 10px 10%; padding: 12px"
      cols="12"
      sm="6"
      offset-sm="3"
    >
      <h2 class="mt-12">나의 강좌</h2>
      <v-btn
        @click="$router.push({ name: 'CreateLecture' })"
        class="ml-auto mt-12"
        >강의 만들기</v-btn
      >
      <vue-slick-carousel
        v-if="Lectures.length"
        class="slick mt-12"
        v-bind="settings"
      >
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
            <v-list-item
              @click="getProblems(video)"
              v-for="(video, idx) in Lecture.videos"
              :key="idx"
            >
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
        <v-card class="mt-12" style="height: 50vh;">
          <h2 class="ml-3">수강 통계</h2>
          <div
            v-if="!totalLectureStatistics"
            class="align-center justify-center"
          >
            <h1 v-if="!totalLectureStatistics" class="mt-16 mx-auto">
              아직 등록된 수강생이 없습니다
            </h1>
          </div>
          <div v-else>
            <v-row align="center" justify="center">
              <v-col>
                <v-progress-circular
                  rotate="-90"
                  size="100"
                  width="15"
                  :value="totalProgress"
                  color="teal"
                  >{{ totalProgress }}</v-progress-circular
                >
                <v-card-text style="font-size: 1em;"
                  >전체 강의 학습률</v-card-text
                >
              </v-col>
              <v-col>
                <v-progress-circular
                  rotate="-90"
                  size="100"
                  width="15"
                  :value="lectureProgress"
                  color="teal"
                  >{{ lectureProgress }}</v-progress-circular
                >
                <v-card-text style="font-size: 1em;"
                  >해당 강의 학습률</v-card-text
                >
              </v-col>
            </v-row>
          </div>
        </v-card>
      </v-col>
    </v-row>
    <v-dialog v-model="problemPopup" persistent>
      <v-row justify="center" style="background-color: #000; padding: 10px">
        <v-col cols="4">
          <v-card height="70vh" style="overflow-y: scroll">
            <h1 style="margin:10px">추천 문제</h1>
            <v-list v-for="(problem, idx) in problemCandidate" :key="idx">
              <v-list-item @click="selectProblem(problem)">
                <v-list-item-content>
                  <v-list-item-title>{{ problem.problem }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-card>
        </v-col>
        <v-col cols="5">
          <v-card class="mb-5" height="45vh">
            <v-textarea outlined label="문제" v-model="problem"></v-textarea>
            <v-textarea outlined label="정답" v-model="answer"></v-textarea>
            <v-btn style="width: 100%" @click="addProblem()">문제 추가</v-btn>
          </v-card>
        </v-col>
        <v-col cols="3">
          <v-card height="45vh" style="overflow-y: scroll; margin-bottom: 15px">
            <v-list v-for="(problem, idx) in problemList" :key="idx">
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title
                    ><strong>{{ idx + 1 }}</strong>
                    {{ problem.problemQuestion }}</v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-card>
          <v-btn @click="submitProblems" width="100%">문제 생성</v-btn>
        </v-col>
        <v-row style="justify-content: flex-end">
          <v-btn class="mr-5" @click="clearProblem">닫기</v-btn>
        </v-row>
      </v-row>
    </v-dialog>
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
// import PieChart from "@/components/Charts/PieChart.vue";

@Component({
  components: {
    VueSlickCarousel
  }
})
export default class InstructorDashboard extends Vue {
  LecturePlayLists = [];
  SelectedPlayLists = [];
  Lectures = [];
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
  // 문제 제출 관련 변수들
  problemPopup = false;
  problemCandidate = [];
  problemList = [];
  // 문제
  selectedproblem = "";
  problem = "";
  answer = "";
  // 통계
  lectureStatistics = null;
  totalLectureStatistics = null;
  totalProgress = null;
  lectureProgress = null;

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
      // 해당 강의 학습률 저장
      this.lectureProgress = Math.round(
        (this.lectureStatistics[this.LecturePlayLists[0].playlistId].complete /
          this.lectureStatistics[this.LecturePlayLists[0].playlistId].total) *
          100
      );
    });
  }

  async getMyPlayList() {
    try {
      const myLectures = await Axios.instance.get(
        "/api/public/playlist/management"
      );
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
    //해당 강의 학습률 확인
    this.lectureProgress = Math.round(
      (this.lectureStatistics[playlistId].complete /
        this.lectureStatistics[playlistId].total) *
        100
    );
  }

  async getProblems(video) {
    console.log(video);
    this.problemPopup = true;
    try {
      const res = await Axios.instanceDjango.post(
        "api/django/summary/qna/",
        {
          video_id: video.video_id
        },
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      if (res.data.data) this.problemCandidate = res.data.data;
    } catch (e) {
      console.error(e);
    }
  }

  selectProblem(problem) {
    this.selectedproblem = problem;
    console.log(this.selectedproblem);
    this.problem = problem.problem;
    this.answer = problem.answer;
  }

  addProblem() {
    if (this.problem && this.answer) {
      this.problemList.push({
        problemCharfield: this.answer,
        problemId: 0,
        problemOrigin: this.selectedproblem.origin,
        problemQuestion: this.problem,
        videoId: this.selectedproblem.video
      });
    }
    this.problem = "";
    this.answer = "";
    this.selectedproblem = "";
  }

  clearProblem() {
    this.problem = "";
    this.answer = "";
    this.selectedproblem = "";
    this.problemCandidate = null;
    this.problemPopup = false;
    this.problemList = "";
  }

  async submitProblems() {
    try {
      const res = await Axios.instance.post(
        "/api/public/problem/save",
        this.problemList,
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      console.log(res);
    } catch (e) {
      console.error(e);
    }
  }

  async getStatistics() {
    try {
      const res = await Axios.instance.get("/api/public/playlist/progress", {});
      // res.data.data = {
      //   "0": {
      //     "total": 7,
      //     "complete" : 2
      //   },
      //   "66": {
      //     "total": 5,
      //     "complete": 1
      //   },
      //   "84": {
      //     "total": 2,
      //     "complete": 1
      //   }
      // }

      if (res.data.data["0"].total !== 0) {
        this.totalLectureStatistics = res.data.data["0"];
        this.totalProgress = Math.round(
          (this.totalLectureStatistics.complete /
            this.totalLectureStatistics.total) *
            100
        );
        this.lectureStatistics = res.data.data;
      }
    } catch (e) {
      console.error(e);
    }
  }

  async created() {
    await this.getStatistics();
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
