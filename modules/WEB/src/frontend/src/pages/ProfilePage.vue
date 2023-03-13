<template>
  <div class="profile">
    <common-form>
      <my-label>Profile Information</my-label>
      <div v-if="userData" class="userDataContainer">
        <user-data :userData="userData" />
        <my-button @click="logout">Logout</my-button>
      </div>
      <div v-else>
        You need to be logged in
      </div>
    </common-form>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import UserData from "@/components/results/UserData.vue";
  import store from "@/store";
  import {UserService} from "@/services/UserService";
  import {mapActions} from "vuex";
  import MyLabel from "@/components/UI/primitives/MyLabel.vue";

  export default defineComponent({
    name: 'ProfilePage',
    components: {
      MyLabel,
      UserData,
    },
    computed: {
      us: () => new UserService(),
      userData: () => store.state.auth.user
    },
    methods: {
      ...mapActions({
        loggedOut: 'auth/loggedOut'
      }),
      async logout() {
        const response = await this.us.logout();
        if (typeof response === 'boolean') {
          await this.loggedOut()
              .then(() => this.$router.push('login'));
        }
      }
    }
});
</script>

<style scoped>
.profile {
  display: flex;
  width: 500px;
  height: auto;
  margin: auto 0px;
}

.userDataContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.userDataContainer button {
  margin-top: 20px;
}
</style>
