<template>
  <div>
    <v-stepper v-model="CreateLectureStep">
      <v-stepper-header>
        <v-stepper-step :complete="CreateLectureStep > 1" step="1">
          강의 정보 입력
        </v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="CreateLectureStep > 2" step="2">
          커리큘럼
        </v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step step="3">
          커버 이미지
        </v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <v-card dark flat class="mb-12" style="margin: 10px 20%">
            <h3 class="mb-0">강의 제목</h3>
            <v-text-field
              class="classdesc mb-0"
              height="20"
              outlined
              v-model="LectureTitle"
            ></v-text-field>
            <h3 class="my-0">강의 설명</h3>
            <v-text-field
              class="classdesc"
              height="20"
              outlined
              v-model="LectureDescription"
            ></v-text-field>
            <h3>카테고리</h3>
            <v-chip-group column v-model="LectureCategory">
              <v-chip label filter outlined>
                개발ㆍ프로그래밍
              </v-chip>
              <v-chip label filter outlined>
                데이터 사이언스
              </v-chip>
              <v-chip label filter outlined>
                크리에이티브
              </v-chip>
              <v-chip label filter outlined>
                업무 스킬
              </v-chip>
              <v-chip label filter outlined>
                직무기초ㆍ학문
              </v-chip>
              <v-chip label filter outlined>
                커리어
              </v-chip>
            </v-chip-group>
            <h3>강의 수준</h3>
            <v-chip-group mandatory v-model="LectureDifficulty">
              <v-chip filter outlined>
                입문
              </v-chip>
              <v-chip filter outlined>
                초급
              </v-chip>
              <v-chip filter outlined>
                중급이상
              </v-chip>
            </v-chip-group>
            <v-btn
              class="mt-12 mx-auto"
              color="primary"
              @click="CreateLectureStep = 2"
            >
              다음 단계로
            </v-btn>
            <v-btn @click="$router.push({name : 'InstructorDashboard' })" class="mt-12">
              취소하기
            </v-btn>
          </v-card>
        </v-stepper-content>

        <v-stepper-content step="2">
          <v-card class="mb-12">
            <v-container fluid class="wrapper">
              <v-row>
                <v-col>
                  <v-card>
                    <h2>내 동영상</h2>
                    <div
                      class="list"
                      style="height: 60vh; width: 40vw; overflow-x: hidden"
                    >
                      <drag
                        v-for="video in UserVideos"
                        :data="video"
                        class="item my-0"
                        :key="video.videoId"
                      >
                        <v-list-item>
                          <v-avatar size="82" class="mr-5" tile>
                            <img
                              :src="video.videoImg"
                              :alt="video.videoTitle"
                            />
                          </v-avatar>
                          <v-list-item-content>
                            <v-list-item-title
                              ><b>{{ video.videoTitle }}</b></v-list-item-title
                            >
                            <v-list-item-content>{{
                              video.videoDescription
                            }}</v-list-item-content>
                          </v-list-item-content>
                        </v-list-item>
                      </drag>
                    </div>
                  </v-card>
                </v-col>
                <v-col>
                  <!--                  <v-btn @click="addSection" tile-->
                  <!--                    ><v-icon left>mdi-plus-box-outline</v-icon>섹션-->
                  <!--                    추가하기</v-btn-->
                  <!--                  >-->
                  <v-card
                    style="height: 65vh; overflow-y: scroll; overflow-x: hidden"
                  >
                    <!--                    <v-card v-for="(Section, idx) in SelectedVideos" :key="idx">-->
                    <!--                      <v-row align="center">-->
                    <!--                        <v-col cols="7">-->
                    <!--                          <v-text-field-->
                    <!--                            placeholder="섹션의 제목을 적어주세요"-->
                    <!--                            class="mt-4 mb-0"-->
                    <!--                            style="width: 35vw;"-->
                    <!--                            v-model="LectureSectionTitles[idx]"-->
                    <!--                            outlined-->
                    <!--                          ></v-text-field>-->
                    <!--                        </v-col>-->
                    <!--                        <v-col cols="5">-->
                    <!--                          <v-btn @click="deleteSection(idx)" large class="mb-4"-->
                    <!--                            >섹션 제거</v-btn-->
                    <!--                          >-->
                    <!--                        </v-col>-->
                    <!--                      </v-row>-->
                    <!--                      <drop-list-->
                    <!--                        :items="Section"-->
                    <!--                        class="list mt-0"-->
                    <!--                        style="min-height: 200px; margin-right: 20px;"-->
                    <!--                        @insert="onInsert($event, Section)"-->
                    <!--                        @reorder="$event.apply(Section)"-->
                    <!--                      >-->
                    <!--                        <template v-slot:item="{ item }">-->
                    <!--                          <drag class="item" :key="item">-->
                    <!--                            <v-list-item>-->
                    <!--                              <v-list-item-content>-->
                    <!--                                <v-list-item-title-->
                    <!--                                  ><strong>{{-->
                    <!--                                    item.title-->
                    <!--                                  }}</strong></v-list-item-title-->
                    <!--                                >-->
                    <!--                                <v-list-item-content>{{-->
                    <!--                                  item.description-->
                    <!--                                }}</v-list-item-content>-->
                    <!--                              </v-list-item-content>-->
                    <!--                            </v-list-item>-->
                    <!--                          </drag>-->
                    <!--                        </template>-->
                    <!--                        <template v-slot:feedback="{ data }">-->
                    <!--                          <div class="item feedback" :key="data">-->
                    <!--                            <v-list-item>-->
                    <!--                              <v-list-item-content>-->
                    <!--                                <v-list-item-title-->
                    <!--                                  ><strong>{{-->
                    <!--                                    data.title-->
                    <!--                                  }}</strong></v-list-item-title-->
                    <!--                                >-->
                    <!--                                <v-list-item-content>{{-->
                    <!--                                  data.description-->
                    <!--                                }}</v-list-item-content>-->
                    <!--                              </v-list-item-content>-->
                    <!--                            </v-list-item>-->
                    <!--                          </div>-->
                    <!--                        </template>-->
                    <!--                      </drop-list>-->
                    <!--                    </v-card>-->
                    <h2>강의 동영상</h2>
                    <drop-list
                      :items="SelectedVideos"
                      class="list"
                      @insert="onInsert"
                      @reorder="$event.apply(SelectedVideos)"
                      style="height: 50vh; overflow-x: hidden; overflow-y: hidden"
                    >
                      <template v-slot:item="{ item }">
                        <drag class="item" :key="item.videoId">
                          <v-list-item>
                            <v-list-item-content>
                              <v-list-item-title
                                ><strong>{{
                                  item.videoTitle
                                }}</strong></v-list-item-title
                              >
                              <v-list-item-content>{{
                                item.videoDescription
                              }}</v-list-item-content>
                            </v-list-item-content>
                          </v-list-item>
                        </drag>
                      </template>
                      <template v-slot:feedback="{ data }">
                        <div class="item feedback" :key="data.videoId">
                          <v-list-item>
                            <v-list-item-content>
                              <v-list-item-title
                                ><strong>{{
                                  data.videoTitle
                                }}</strong></v-list-item-title
                              >
                              <v-list-item-content>{{
                                data.videoDescription
                              }}</v-list-item-content>
                            </v-list-item-content>
                          </v-list-item>
                        </div>
                      </template>
                    </drop-list>
                    <v-btn
                      class="ml-12"
                      color="primary"
                      @click="CreateLectureStep = 3"
                    >
                      다음 단계로
                    </v-btn>

                    <v-btn @click="CreateLectureStep = 1">
                      이전 단계로
                    </v-btn>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
          </v-card>
        </v-stepper-content>

        <v-stepper-content step="3">
          <v-row>
            <v-col cols="6">
              <!--              <v-card class="mb-12">-->
              <!--                <v-list v-for="(Section, idx) in SelectedVideos" :key="idx">-->
              <!--                  <h2>섹션 {{ idx + 1 }} : {{ LectureSectionTitles[idx] }}</h2>-->
              <!--                  <v-list-item v-for="Video in Section" :key="Video.videoId">-->
              <!--                    <v-avatar size="82" class="mr-5" tile>-->
              <!--                      <img :src="Video.thumbnailurl" :alt="Video.title" />-->
              <!--                    </v-avatar>-->
              <!--                    <v-list-item-content>-->
              <!--                      <v-list-item-title-->
              <!--                        ><strong>{{ Video.title }}</strong></v-list-item-title-->
              <!--                      >-->
              <!--                      <v-list-item-content>{{-->
              <!--                        Video.description-->
              <!--                      }}</v-list-item-content>-->
              <!--                    </v-list-item-content>-->
              <!--                  </v-list-item>-->
              <!--                </v-list>-->
              <!--              </v-card>-->
              <v-card
                class="mb-12"
                style="max-height: 80vh; overflow-y: scroll"
              >
                <h2>강의 목록</h2>
                <v-list-item
                  v-for="Video in SelectedVideos"
                  :key="Video.videoId"
                >
                  <v-avatar size="82" class="mr-5" tile>
                    <img :src="Video.videoImg" :alt="Video.videoTitle" />
                  </v-avatar>
                  <v-list-item-content>
                    <v-list-item-title
                      ><strong>{{
                        Video.videoTitle
                      }}</strong></v-list-item-title
                    >
                    <v-list-item-content>{{
                      Video.videoDescription
                    }}</v-list-item-content>
                  </v-list-item-content>
                </v-list-item>
              </v-card>
            </v-col>
            <v-col cols="6">
              <v-card>
                <h2 class="mb-5">커버 이미지</h2>
                <v-row>
                  <v-col cols="6">
                    <v-img
                      v-if="LectureThumbnailURL"
                      class="ml-15"
                      width="320"
                      height="200"
                      :src="LectureThumbnailURL"
                      alt="강의 썸네일 이미지"
                    ></v-img>
                    <v-img
                      v-else
                      class="ml-15"
                      width="320"
                      height="200"
                      src="@/assets/jun.png"
                    ></v-img>
                  </v-col>
                  <v-col cols="6">
                    <h5 class="mt-10 mb-3">강의를 대표하는 이미지</h5>
                    <v-file-input
                      v-model="LectureThumbnail"
                      color="deep-purple accent-4"
                      counter
                      label="File input"
                      placeholder="Select your files"
                      prepend-icon="mdi-paperclip"
                      outlined
                      @change="thumbnail"
                    >
                      <template v-slot:selection="{ index, text }">
                        <v-chip color="deep-purple accent-4" dark label small>
                          {{ text }}
                        </v-chip>
                      </template>
                    </v-file-input>
                  </v-col>
                </v-row>
                <h2>저작물 활용 동의서</h2>
                <v-textarea
                  class="my-0"
                  disabled
                  outlined
                  value="본인은 우리서비스 가 본인이 제작한 강의를 비영리적인 목적으로 활용(전체, 인용, 발췌, 편집)하거나 출판, 공표, 복제, 공연, 방송, 전시 및 배포 할 권리와 수정, 변경, 삭제 등 2차적 제작물 또는 편집 제작물 작성 등 관련 지적재산권 일체를 포괄적으로 이용할 권리를 갖는 것에 동의합니다."
                >
                </v-textarea>
                <v-checkbox
                  class="mt-0"
                  label="내용을 충분히 이해하였으며, 이에 동의합니다."
                  v-model="CopyrightAgreement"
                ></v-checkbox>
                <v-btn color="primary" @click="createLecture">
                  강의 생성
                </v-btn>

                <v-btn @click="CreateLectureStep = 2">
                  이전 단계로
                </v-btn>
              </v-card>
            </v-col>
          </v-row>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </div>
</template>

<script>
import { Vue, Component, Watch } from "vue-property-decorator";
import { Drag, DropList } from "vue-easy-dnd";
import { Axios } from "@/service/axios.service";
//import { PlayList } from "@/store/Instructor.interface";
import axios from "axios";

@Component({
  components: {
    Drag,
    DropList
  }
})
export default class CreateLecture extends Vue {
  UserVideos = [
    {
      videoId: "t8sjTFM_tfE",
      videoTitle: "0.1초 동안 컴퓨터를 빌려보자 - AWS Lambda",
      videoDescription:
        "0.1초 동안만 컴퓨터를 빌릴 수 있다면 얼마나 좋을까요? 0.1초 단위로 컴퓨터를 임대해주는 아마존 웹서비스 람다 수업을 만들었습니다. 람다의 실행방법과 디버깅 ...",
      videoImg: "https://i.ytimg.com/vi/t8sjTFM_tfE/hqdefault.jpg",
      videoMaxImg: 0,
      userId: "test@test.com"
    },
    {
      videoId: "87Ra6xwepFI",
      videoTitle: "Machine learning 1 - 6. 모델",
      videoDescription:
        "이 수업은 머신러닝을 처음 시작하는 분들이 이론없이, 수학없이, 코딩없이 머신러닝을 경험해볼 수 있도록 고안된 수업입니다. 이 수업이 끝나고 나면 머신러닝이 ...",
      videoImg: "https://i.ytimg.com/vi/87Ra6xwepFI/hqdefault.jpg",
      videoMaxImg: 0,
      userId: "test@test.com"
    },
    {
      videoId: "F5SUlHhjYCk",
      videoTitle: "Machine learning 1 - 5. Teachable machine",
      videoDescription:
        "이 수업은 머신러닝을 처음 시작하는 분들이 이론없이, 수학없이, 코딩없이 머신러닝을 경험해볼 수 있도록 고안된 수업입니다. 이 수업이 끝나고 나면 머신러닝이 ...",
      videoImg: "https://i.ytimg.com/vi/F5SUlHhjYCk/hqdefault.jpg",
      videoMaxImg: 0,
      userId: "test@test.com"
    },
    {
      videoId: "KR8ddnPjCtk",
      videoTitle: "Machine learning 1 - 2. 머신러닝이란?",
      videoDescription:
        "이 수업은 머신러닝을 처음 시작하는 분들이 이론없이, 수학없이, 코딩없이 머신러닝을 경험해볼 수 있도록 고안된 수업입니다. 이 수업이 끝나고 나면 머신러닝이 ...",
      videoImg: "https://i.ytimg.com/vi/KR8ddnPjCtk/hqdefault.jpg",
      videoMaxImg: 0,
      userId: "test@test.com"
    },
    {
      videoId: "LPqmPfhnR1o",
      videoTitle: "Machine learning 1 - 1. 오리엔테이션",
      videoDescription:
        "이 수업은 머신러닝을 처음 시작하는 분들이 이론없이, 수학없이, 코딩없이 머신러닝을 경험해볼 수 있도록 고안된 수업입니다. 이 수업이 끝나고 나면 머신러닝이 ...",
      videoImg: "https://i.ytimg.com/vi/LPqmPfhnR1o/hqdefault.jpg",
      videoMaxImg: 0,
      userId: "test@test.com"
    }
  ];
  CreateLectureStep = 1;

  SelectedVideos = [];
  LectureTitle = "";
  LectureDescription = "";
  LecturePrerequisite = "";
  LectureCategory = "";
  LectureSectionTitles = [];
  LectureDifficulty = "";
  LectureThumbnail = null;
  LectureThumbnailURL = null;
  LectureThumbnailLink = null;
  LectureTrackId = null;
  CopyrightAgreement = false;

  onInsert(event) {
    this.SelectedVideos.splice(event.index, 0, event.data);
    this.UserVideos = this.UserVideos.filter(item => item !== event.data);
  }

  // addSection() {
  //   this.SelectedVideos.push([]);
  //   this.LectureSectionTitles.push("");
  // }

  // deleteSection(idx) {
  //   this.SelectedVideos[idx].map(elem => this.UserVideos.push(elem));
  //   this.SelectedVideos.sort();
  //   this.SelectedVideos.splice(idx, 1);
  // }

  async thumbnail() {
    if (this.LectureThumbnail) {
      this.LectureThumbnailURL = URL.createObjectURL(this.LectureThumbnail);
      try {
        const file = new FormData();
        file.append("file", this.LectureThumbnail);
        const res = await Axios.instance.post(
          "/api/public/playlist/imgupload",
          file,
          {
            headers: {
              "Content-Type": "multipart/form-data"
            }
          }
        );
        if (res) {
          const link = "http://k3b108.p.ssafy.io/file/playlist/";
          this.LectureThumbnailLink = link + res.data.data;
        }
      } catch (e) {
        console.error(e);
      }
    }
  }

  async createPlayList() {
    try {
      const res = await Axios.instance.post(
        "/api/private/playlist/save",
        {
          playlistCategory: this.LectureCategory,
          playlistDescription: this.LectureDescription,
          playlistId: 0,
          playlistImg: this.LectureThumbnailLink,
          playlistLevel: 0,
          playlistTitle: this.LectureTitle,
          playlistType: 0,
          userId: ""
        },
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      console.log(res);
      if (res.data.data) this.LectureTrackId = res.data.data.playlistId;
    } catch (e) {
      console.error(e);
    }
  }

  async getVideoList() {
    try {
      const res = await axios.get(
        "http://k3b108.p.ssafy.io:8080/api/public/videos",
        {
          headers: {
            "Content-Type": "application/json",
            Authorization:
              "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjoxNjA2MTE2MjUxfQ.rPtgy9ukKU1sC9jC37t7hTTyVsAxnyX7Wt1Ei_PIJdYvjBphHN8S9MfWIH6g-5WRI9RQkhRlw13GfJ9UkfeZ4w"
          }
        }
      );
      console.log(res);
      if (res.data.data != null) this.UserVideos = res.data.data;
    } catch (e) {
      console.error(e);
    }
  }

  async addVideo() {
    try {
      const res = await Axios.instance.post(
        "/api/pirvate/playlist/addvideo",
        this.SelectedVideos,
        {
          params: {
            playlistId: this.LectureTrackId
          },
          headers: {
            "Content-Type": "application/json",
          }
        }
      );
      console.log(res);
    } catch (e) {
      console.error(e);
    }
  }

  async djangoVideoAnalysis() {
    try {
      const videoIDs = [];
      this.SelectedVideos.map(elem => videoIDs.push(elem.videoId));
      const res = await Axios.instanceDjango.post(
        "api/django/summary/extract/",
        videoIDs,
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      console.log(res);
    } catch (e) {
      console.log("어쨋든 요청은 보냄");
    }
  }

  async createLecture() {
    if(!this.CopyrightAgreement) {
      alert("저작권 활용 동의가 필요합니다.")
    } else {
      await this.thumbnail();
      await this.createPlayList();
      await this.addVideo();
      await this.djangoVideoAnalysis();
      alert("강의가 생성되었습니다");
      await this.$router.push({name: "InstructorDashboard"});
    }
  }
  created() {
    this.getVideoList();
  }
}
</script>

<style lang="scss" scoped>
html,
body,
#app,
.v-application--wrap,
.v-content,
.v-content__wrap {
  height: 100%;
}

.drop-in {
  box-shadow: 0 0 10px rgba(0, 0, 255, 0.3);
}
</style>

<style scoped lang="scss">
.classdesc {
  width: 45vw;
}

.wrapper {
  .list {
    border: 1px solid black;
    margin: 10px auto;
    overflow-y: scroll;
    .item {
      margin: 10px;
      display: flex;
      align-items: center;
      justify-content: center;

      &.feedback {
        background-color: rgb(255, 220, 220);
        border: 2px dashed black;
      }

      &.drag-image {
        background-color: rgb(220, 255, 220);
        transform: translate(-50%, -50%);
      }
    }
  }
}
</style>
