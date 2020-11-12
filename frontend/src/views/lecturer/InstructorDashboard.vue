<template>
  <div>
    <v-row style="margin: 10px 10%" cols="12" sm="6" offset-sm="3">
      <h2 class="mt-12">나의 강좌</h2>
      <vue-slick-carousel v-if="Lectures" class="slick mt-12" v-bind="settings">
        <div v-for="lecture in Lectures" :key="lecture.LectureId">
          <router-link
            class="router-link"
            :to="{
              name: 'DetailArtView',
              params: { LectureId: lecture.LectureId }
            }"
          >
            <img
              class="Lecture-img"
              :src="lecture.LectureThumb"
              :alt="lecture.LectureName"
            />
          </router-link>
        </div>
      </vue-slick-carousel>
    </v-row>
    <v-row style="margin: 10px 10%">
      <v-col cols="5">
        <v-card class="mt-12">
          <h2 class="ml-3">최근 질문</h2>
          <v-list three-line>
            <template v-for="(item, index) in items">
              <v-subheader
                v-if="item.header"
                :key="item.header"
                v-text="item.header"
              ></v-subheader>

              <v-divider
                v-else-if="item.divider"
                :key="index"
                :inset="item.inset"
              ></v-divider>

              <v-list-item v-else :key="item.title">
                <v-list-item-avatar>
                  <v-img :src="item.avatar"></v-img>
                </v-list-item-avatar>

                <v-list-item-content>
                  <v-list-item-title v-html="item.title"></v-list-item-title>
                  <v-list-item-subtitle
                    v-html="item.subtitle"
                  ></v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </template>
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
  data() {
    return {
      Lectures: [
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
      ],
      items: [
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
          title: "Brunch this weekend?",
          subtitle: "h"
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
          title: "dsf",
          subtitle: "hi"
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
          title: "Oui oui",
          subtitle: "jo"
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
          title: "Birthday gift",
          subtitle: "fsd"
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
          title: "Recipe to try",
          subtitle: "nana"
        }
      ],
      settings: {
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
      }
    };
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
