<template>
  <v-card class="mx-auto" min-width="344" width="100%" height="250" outlined>
    <v-card-title class="headline mb-1">
      내 프로필
    </v-card-title>
    <v-list-item>
      <v-list-item-avatar tile size="80" color="grey"></v-list-item-avatar>
      <v-list-item-content>
        <div class="overline">
          닉네임 : 학생
        </div>
        <div class="overline">
          이메일 : 지메일
        </div>
      </v-list-item-content>
    </v-list-item>

    <v-card-actions class="mb-7 mt-2">
      <v-btn outlined rounded text right absolute>
        수정
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Profile extends Vue {}
</script>

<style></style>
