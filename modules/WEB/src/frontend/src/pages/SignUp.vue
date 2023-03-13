<template>
  <div class="sign_up_page">
    <common-form id="submit_form">
      <div v-if="hasError">
        <error-message>{{errorMsg}}</error-message>
      </div>
      <label-input id="li" type="text" label="Login" placeholder="Type Login" v-model="form.loginInput"/>
      <label-input id="ei" type="email" label="Email" placeholder="Type Email" v-model="form.emailInput"/>
      <label-input id="pi1" type="password" label="Password" placeholder="Password..." v-model="form.passwordInput1" />
      <label-input id="pi2" type="password" label="Repeat" placeholder="Password..." v-model="form.passwordInput2" />
      <my-button @click="register">Register</my-button>
    </common-form>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import { UserService } from '@/services/UserService';
  import ErrorMixin from "@/components/mixins/errorMixin";
  import ErrorMessage from "@/components/UI/primitives/ErrorMessage.vue";

  export default defineComponent({
    name: "SignUpPage",
    components: {ErrorMessage},
    mixins: [ErrorMixin],
    data() {
      return {
        form : {
          loginInput: '',
          emailInput: '',
          passwordInput1: '',
          passwordInput2: '',
        }
      }
    },
    methods: {
      async register() {
        if (this.form.loginInput === '' || this.form.emailInput === '' || this.form.passwordInput1 === '')
          this.errorMsg = "Some fields are empty";
        else if (this.form.passwordInput1 != this.form.passwordInput2)
          this.errorMsg = "Passwords differ";
        else {
          const response = await UserService.signUp(this.form.loginInput, this.form.emailInput, this.form.passwordInput1);
          if (typeof response === 'boolean')
            await this.$router.push("/login");
          else
            this.errorMsg = response.error;
        }
      }
    }
  })
</script>

<style scoped>
  .sign_up_page {
    display: flex;
    width: 500px;
    height: auto;
    margin: auto 0;
  }

  #submit_form > * {
    margin: 15px 0px;
  }
</style>