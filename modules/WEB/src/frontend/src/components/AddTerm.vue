<template>
  <div class="add-invite" v-if="!createForm">
    <slot/>
    <my-button @click="createForm=true" :classes="'add'">Create</my-button>
  </div>
  <div class="add-form" v-else>
    <label-input label="TermName:" placeholder="Input..." v-model="addTerm.name"></label-input>
    <my-area placeholder="Description..." v-model="addTerm.description">Description</my-area>
    <div class="buttons">
      <my-button @click="createForm=false" :classes="'remove'">Close</my-button>
      <my-button @click="createTerm">Add</my-button>
    </div>
    <div v-if="hasError">
      <error-message>{{errorMsg}}</error-message>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {CreateTerm, TermService} from "@/services/TermService";
  import errorMixin from "@/components/mixins/errorMixin";
  import MyArea from "@/components/UI/composits/MyArea.vue";
  import MyButton from "@/components/UI/primitives/MyButton.vue";
  export default defineComponent({
    name: "AddTerm",
    components: {MyButton, MyArea},
    mixins: [errorMixin],
    data() : {createForm: boolean, addTerm: CreateTerm} {
      return {
        createForm: false,
        addTerm: {
          name: '',
          description: ''
        }
      }
    },
    computed: {
      us: () => new TermService()
    },
    methods: {
      async createTerm() {

        const response = await this.us.addTerm(this.addTerm);
        if (response === null) {
          this.errorMsg = '';
          this.createForm = false;
          this.addTerm.name = '';
          this.addTerm.description = '';
        } else {
          this.errorMsg = response.error;
        }
      }
    }
  })
</script>

<style scoped>
  .add-invite {
    display: flex;
  }

  .add-invite > * {
    margin: 10px 5px;
  }

  .add-form {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
  }

  .add-form > * {
    margin-bottom: 15px;
  }
  .buttons {
    display: flex;
  }
  .buttons > * {
    margin-left: 10px;
  }
</style>