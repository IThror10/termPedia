<template>
  <div class="user-data">
    <label-value label="Login" :value="userData.login"></label-value>
    <label-value label="Email" :value="userData.email"></label-value>
    <label-list @add="addPhone" @remove="removePhone" label="Phones" :values="userData.phones"></label-list>
    <label-list @add="addPost" @remove="removePost" label="Posts" :values="userData.posts"></label-list>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {UserData, UserService} from '@/services/UserService';
  import {mapActions} from "vuex";

  export default defineComponent({
    name: 'UserData',
    props: {
      userData: {
        type: Object as () => UserData,
        required: true
      }
    },
    computed: {
      us: () => new UserService()
    },
    methods: {
      ...mapActions({
        dataChanged: 'auth/dataChanged'
      }),
      async addPhone(value: string) {
        await this.changeData('phone', 'add', value)
      },
      async removePhone(value: string) {
        await this.changeData('phone', 'remove', value)
      },
      async addPost(value: string) {
        await this.changeData('post', 'add', value)
      },
      async removePost(value: string) {
        await this.changeData('post', 'remove', value)
      },
      async changeData(field: string, op: string, value: string) {
        const response = await this.us.changeUserContacts(field, op, value);
        if ('posts' in response) {
          await this.dataChanged({inData: response});
        }
      }
    }
  });
</script>

<style scoped>
  .user-data {
    width: 100%;
    height: auto;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
  }
</style>