<template>
  <div>
    <v-row>
      <v-col v-for="(Question, idx) in Questions" :key="idx" cols="12">
        <v-card>{{ Question.no }}</v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component
export default class InstructorFeedback extends Vue {
  Questions = [{ no: "1123" }, { no: "12123" }];
}
</script>

<style scoped></style>
