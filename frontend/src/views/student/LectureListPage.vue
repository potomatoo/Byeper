<template>
  <div class="lecture-list-page mx-auto mt-10">
    <div class="text-h3 my-5">
      재생목록 페이지
    </div>
    <statistics-by-cource />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import StatisticsByCource from "@/components/dashboard/StatisticsByCource.vue";

@Component({
  components: {
    StatisticsByCource
  }
})
export default class LectureListPage extends Vue {}
</script>

<style>
.lecture-list-page {
  width: 80%;
}
</style>
