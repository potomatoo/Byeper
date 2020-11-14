<template>
  <div class="quiz">
    <v-row class="quiz-content" align="center">
      <v-col class="quiz-col" cols="11">
        <v-window v-model="window" vertical>
          <v-window-item v-for="Quiz in Quizes" :key="Quiz.id">
            <v-row class="quiz-text" align="center" justify="center">
              <v-col cols="12">
                <p style="text-align: center">
                  {{ Quiz.text }}
                </p>
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
          </v-window-item>
        </v-window>
      </v-col>
      <v-col cols="1">
        <v-item-group
          v-model="window"
          class="shrink mr-6"
          mandatory
          tag="v-flex"
        >
          <v-item v-for="n in length" :key="n" v-slot="{ active, toggle }">
            <div>
              <v-btn :input-value="active" icon @click="toggle">
                <v-icon>mdi-record</v-icon>
              </v-btn>
            </div>
          </v-item>
        </v-item-group>
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Quiz extends Vue {
  length = 3;
  window = 0;

  Quizes = [
    { id: 1, text: "자료구조는 크게 ____구조와 ____구조로 나뉜다." },
    { id: 2, text: "DFS의 장점은 ____를 적게 사용한다." },
    { id: 3, text: "데이터 분석을 할 때 가장 많이 쓰이는 언어는 ____이다." }
  ];

  next() {
    this.window = this.window + 1 === this.length ? 0 : this.window + 1;
  }
  prev() {
    this.window = this.window - 1 < 0 ? this.length - 1 : this.window - 1;
  }

  mounted() {
    const editorContainer: HTMLElement = document.querySelector(
      ".editor-container"
    ) as HTMLElement;
    editorContainer.style.backgroundColor = "#1E1E1E";
    editorContainer.style.paddingBottom = "0";
  }
}
</script>

<style scoped>
.quiz {
  width: 100%;
}
.quiz-content {
  width: 100%;
  height: calc(100vh - 100px);
  padding: 0;
}
.quiz-text {
  width: 100%;
  height: calc(100vh - 100px);
  background-color: gray;
  margin: 0;
}
.quiz-col {
  padding-top: 0;
  padding-bottom: 0;
}
</style>
