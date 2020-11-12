<template>
  <div>
    <play-list-description />
    <v-divider
      class="light"
      style="font-weight: bold; margin-top: 10px; "
    ></v-divider>
    <play-list-videos />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import PlayListDescription from "@/components/main/PlayListDescription.vue";
import PlayListVideos from "@/components/main/PlayListVideos.vue";

@Component({
  components: {
    PlayListDescription,
    PlayListVideos
  }
})
export default class PlayList extends Vue {}
</script>
