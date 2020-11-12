<template>
  <div class="quiz">
    <v-card flat tile width="100%">
      <v-window v-model="onboarding" vertical>
        <v-window-item v-for="Quiz in Quizes" :key="Quiz.id">
          <v-card class="quiz-content" color="grey">
            <v-row align="center" justify="center">
              <v-col cols="12">
                <h1
                  style="font-size: 1rem; text-align: center;"
                  class="white--text"
                >
                  {{ Quiz.text }}
                </h1>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  label="Solo"
                  placeholder="정답:"
                  solo
                  width="50px"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-card>
        </v-window-item>
      </v-window>

      <v-card-actions class="justify-space-between">
        <v-btn text @click="prev">
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
        <v-item-group v-model="onboarding" class="text-center" mandatory>
          <v-item
            v-for="n in length"
            :key="`btn-${n}`"
            v-slot="{ active, toggle }"
          >
            <v-btn :input-value="active" icon @click="toggle">
              <v-icon>mdi-record</v-icon>
            </v-btn>
          </v-item>
        </v-item-group>
        <v-btn text @click="next">
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Quiz extends Vue {
  length = 3;
  onboarding = 0;

  Quizes = [
    { id: 1, text: "자료구조는 크게 ____구조와 ____구조로 나뉜다." },
    { id: 2, text: "DFS의 장점은 ____를 적게 사용한다." },
    { id: 3, text: "데이터 분석을 할 때 가장 많이 쓰이는 언어는 ____이다." }
  ];

  mounted() {
    const editorContainer: HTMLElement = document.querySelector(
      ".editor-container"
    ) as HTMLElement;

    editorContainer.style.backgroundColor = "#1E1E1E";
    editorContainer.style.paddingBottom = "0";
  }

  next() {
    this.onboarding =
      this.onboarding + 1 === this.length ? 0 : this.onboarding + 1;
  }
  prev() {
    this.onboarding =
      this.onboarding - 1 < 0 ? this.length - 1 : this.onboarding - 1;
  }
}
</script>

<style scoped>
.quiz {
  width: 100%;
  height: calc(100%-64px);
}
.quiz-content {
  height: calc(100vh - 156px);
  align-items: center;
  display: flex;
}
</style>
