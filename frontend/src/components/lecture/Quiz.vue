<template>
  <div>
    <v-container v-if="ProblemList && !ProblemList.length">
      <v-row class="quiz-container" justify="center" align="center">
        <v-col cols="7" style="text-align: center">
          <v-icon style="font-size: 200px;">mdi-folder-remove-outline</v-icon>
          <div style="font-size: 30px">
            등록된 퀴즈가 없습니다.
          </div>
        </v-col>
      </v-row>
    </v-container>

    <div class="quiz">
      <v-row
        class="quiz-content"
        align="center"
        v-if="ProblemList && ProblemList.length"
      >
        <v-col class="quiz-col" cols="11">
          <v-window v-model="window" vertical>
            <v-window-item v-for="Quiz in ProblemList" :key="Quiz.id">
              <v-row class="quiz-text" align="center" justify="center">
                <v-col cols="12">
                  <p style="text-align: center">
                    {{ Quiz.problemQuestion }}
                  </p>
                </v-col>
                <v-col cols="4">
                  <v-text-field
                    v-model="nowAnswer"
                    label="Solo"
                    placeholder="정답:"
                    solo
                    width="50px"
                  ></v-text-field>
                  <v-btn
                    class="quiz-submitBtn"
                    @click="CheckAnswer(Quiz.problemCharfield)"
                    >정답 확인</v-btn
                  >
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
            <v-item
              v-for="n in Problemlength"
              :key="n"
              v-slot="{ active, toggle }"
            >
              <div>
                <v-btn :input-value="active" icon @click="toggle">
                  <v-icon>mdi-record</v-icon>
                </v-btn>
              </div>
            </v-item>
          </v-item-group>
        </v-col>
      </v-row>

      <div class="text-center">
        <v-snackbar
          class="answerSnackbar"
          v-model="CorrectSnackbar"
          timeout="2000"
          absolute
          centered
        >
          <div class="text-center" style="color: #2196F3; font-size: 30px;">
            <v-icon style="color: #2196F3; font-size: 30px;"
              >mdi-checkbox-marked-circle-outline</v-icon
            >
            정답입니다!
          </div>
        </v-snackbar>

        <v-snackbar
          class="answerSnackbar"
          v-model="WrongtSnackbar"
          timeout="2000"
          absolute
          centered
        >
          <div class="text-center" style="color: #E91E63; font-size: 30px;">
            <v-icon style="color: #E91E63; font-size: 30px;"
              >mdi-alert-circle-check-outline</v-icon
            >
            오답입니다!
          </div>
        </v-snackbar>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { Lecture, ProblemList } from "../../store/Lectures.interface";

const LecturesModule = namespace("LecturesModule");

@Component
export default class Quiz extends Vue {
  @LecturesModule.State lecture!: Lecture;
  @LecturesModule.State ProblemList!: ProblemList[];

  Problemlength = 0;
  window = 0;
  nowAnswer = "";
  CorrectSnackbar = false;
  WrongtSnackbar = false;

  mounted() {
    const editorContainer: HTMLElement = document.querySelector(
      ".editor-container"
    ) as HTMLElement;
    editorContainer.style.backgroundColor = "#1E1E1E";
    editorContainer.style.paddingBottom = "0";
  }

  CheckAnswer(submitAnswer: string) {
    if (this.nowAnswer === submitAnswer) {
      this.CorrectSnackbar = true;
      this.window++;
      this.nowAnswer = "";
    } else {
      this.WrongtSnackbar = true;
    }
  }

  created() {
    this.Problemlength = this.ProblemList.length;
  }
}
</script>

<style scoped>
.quiz-container {
  height: calc(100vh - 124px);
}
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
.quiz-submitBtn {
  position: absolute;
  bottom: 30px;
  right: 30px;
}
</style>
