<template>
  <v-card
    v-if="user"
    class="mx-auto"
    min-width="344"
    width="100%"
    height="250"
    outlined
  >
    <v-card-title class="headline mb-1">
      내 프로필
    </v-card-title>
    <v-list-item>
      <v-list-item-content>
        <div class="overline" style="text-transform: none">
          닉네임 : {{ user.userName }}
        </div>
        <div class="overline" style="text-transform: none">
          이메일 : {{ user.userId }}
        </div>
      </v-list-item-content>
    </v-list-item>

    <div class="text-center">
      <v-dialog v-model="dialog" width="500">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            class="mb-7 mt-2"
            dark
            v-bind="attrs"
            v-on="on"
            outlined
            rounded
            text
            right
            absolute
          >
            수정
          </v-btn>
        </template>

        <v-card>
          <v-card-title class="headline">
            회원정보 수정
          </v-card-title>

          <v-divider></v-divider>

          <v-text-field
            v-model="newName"
            :counter="10"
            label="새로운 닉네임을 입력하세요."
            class="ml-5 mr-5"
          ></v-text-field>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="submitName">
              수정
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { User } from "../../store/Accounts.interface";

const AccountsModule = namespace("AccountsModule");

@Component
export default class Profile extends Vue {
  @AccountsModule.State user!: User | null;
  @AccountsModule.Action CHANGE_USER_NAME: any;

  dialog = false;
  newName = null;

  async submitName() {
    await this.CHANGE_USER_NAME(this.newName);
    this.dialog = false;
  }
}
</script>

<style></style>
