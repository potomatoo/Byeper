<template>
  <div class="lecture-list-page mx-auto mt-10">
    <div class="text-h3 my-5">
      {{ this.$route.params.courseName }}
    </div>
    <statistics-by-cource />
    <play-list-videos />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import StatisticsByCource from "@/components/dashboard/StatisticsByCource.vue";
import PlayListVideos from "@/components/main/PlayListVideos.vue";

@Component({
  components: {
    StatisticsByCource,
    PlayListVideos
  }
})
export default class LectureListPage extends Vue {}
</script>

<style>
.lecture-list-page {
  width: 80%;
}
</style>
