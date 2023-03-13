<template>
  <div class="add-lit-form">
    <div class="form">
      <label-input
          v-model="ref_form.name"
          label="Name"
      />
      <label-input
          v-model="ref_form.year"
          label="Year"
      />
      <label-input
        label="Type"
        placeholder="Book"
        v-model="ref_form.type"
      />
      <label-list
          label="Authors"
          :values="form.authors"
          @add="addAuthor"
          @remove="removeAuthor"
      />

    </div>
    <div class="buttons">
      <remove-button @click="$emit('close')">Close</remove-button>
      <add-button @click="$emit('create', ref_form)">Create</add-button>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent, ref} from "vue";
  import {LiteratureData} from "@/services/LiteratureService";
  import LabelInput from "@/components/UI/composits/LabelInput.vue";
  import LabelList from "@/components/UI/composits/LabelList.vue";

  export default defineComponent({
    name: "AddLit",
    components: {LabelList, LabelInput},
    emits: ['close', 'create'],
    props: {
      form: {
        type: Object as () => LiteratureData,
        required: true
      }
    },
    setup(props) {
      const ref_form = ref<LiteratureData>(props.form)
      return { ref_form }
    },
    methods: {
      addAuthor(name: string) {
        if (!this.ref_form.authors.some(author => author === name))
          this.ref_form.authors.push(name);
      },
      removeAuthor(name: string) {
        this.ref_form.authors = this.ref_form.authors.filter(tag => tag !== name)
      }
    },
  })
</script>

<style scoped>
  .add-lit-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 10px auto;
  }
  .add-lit-form > * {
    margin: 5px 0;
  }
</style>