<template>
  <header>
    <h2>TermPedia</h2>
    <div class="links">
      <HeaderLink v-if="!authorized" class="link" to="/login" exact>Login</HeaderLink>
      <HeaderLink v-else class="link" to="/profile" exact>My Profile</HeaderLink>
      <HeaderLink class="link" to="/terms" exact>Terms</HeaderLink>
      <HeaderLink class="link" to="/" exact>Literature</HeaderLink>
    </div>
  </header>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import HeaderLink from "@/components/header/HeaderLink.vue";
  import store from '@/store';

  export default defineComponent({
    name: 'AppHeader',
    components: {
      HeaderLink
    },
    computed: {
      authorized: () => store.state.auth.isAuth
    },
  });
</script>

<style scoped>
  header {
    background-color: #333;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 15px;
  }

  header h2 {
    font-size: 22px;
  }

  header .link {
    margin: 0px 15px 0px 15px;
  }
</style>