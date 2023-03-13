<template>
  <div class = "sign_in_page">
    <common-form>
      <error-message v-show="hasError">{{errorMsg}}</error-message>
      <label-input id="li" type="text" label="Login" placeholder="Enter Login" v-model="form.login"/>
      <label-input id="pi" type="password" label="Password" placeholder="Enter Password" v-model="form.password" />
      <div class="buttons">
        <my-button @click="login">Login</my-button>
        <my-button @click="register">Registration</my-button>
      </div>
    </common-form>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {UserService} from "@/services/UserService";
  import {mapActions} from "vuex";
  import ErrorMessage from "@/components/UI/primitives/ErrorMessage.vue";
  import errorMixin from "@/components/mixins/errorMixin";

  export default defineComponent({
    name: "SignInPage",
    components: {ErrorMessage},
    mixins: [errorMixin],
    data() {
      return {
        form: {
          login: '',
          password: ''
        },
      }
    },
    computed: {
      us: () => new UserService(),
    },
    methods: {
      ...mapActions({
        loggedIn: 'auth/loggedIn'
      }),
      async login() {
        const response = await this.us.signIn(this.form);
        if ('token' in response)
          await this
              .loggedIn({inData: response})
              .then(() => this.$router.push('/profile'));
        else
          this.errorMsg = response.error;
      },
      async register() {
        this.$router.push('/register');
      }
    }
  })
</script>

<style scoped>
  .sign_in_page {
    width: 500px;
    height: 60%;
    margin: auto 0;
  }

  .buttons button {
    margin: 20px 10px;
  }

  #li, #pi {
    margin-top: 10px;
    margin-bottom: 30px;
  }
</style>