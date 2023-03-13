<template>
  <div class="tags">
    <div class="tag-header">
      <my-label class="tag-label">Tags</my-label>
      <my-button @click="searchTags(curPage)">Refresh</my-button>
    </div>
    <separate-line/>

    <DropdownInput
        placeholder="Tag Name..."
        v-model="searchName"
        @add="addTag"
    >
      <common-list :not-empty="toSelect.length > 0">
        <framed-list-item v-for="(tag, index) in toSelect" :key="index" @click="choose(index)">
          <tag-name-item :tag="tag"/>
        </framed-list-item>
      </common-list>
    </DropdownInput>

    <div v-if="hasError">
      <error-message>{{errorMsg}}</error-message>
    </div>
    <separate-line/>

    <search-component class="tag-searcher"
        @search="searchTags"
        :has-input="false"
        :cur-page="curPage"
        :has-more="pageSize === data.length"
    >
      <common-list :not-empty="data.length > 0">
        <list-item v-for="(tag, index) in data" :key="index">
          <tag-item :term-id="termId" :tag="tag"/>
        </list-item>
      </common-list>
    </search-component>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import SearchComponent from "@/components/SearchComponent.vue";
  import {TagData, TagName, TagService} from "@/services/TagService";
  import DropdownInput from "@/components/UI/composits/DropdownInput.vue";
  import pathMixin from "@/components/mixins/pathMixin";
  import errorMixin from "@/components/mixins/errorMixin";
  import store from "@/store";
  import CommonList from "@/components/UI/list/CommonList.vue";
  import FramedListItem from "@/components/UI/list/FramedListItem.vue";
  import TagItem from "@/components/results/Tag/TagItem.vue";
  import ListItem from "@/components/UI/list/ListItem.vue";
  import TagNameItem from "@/components/results/Tag/TagName.vue";

  export default defineComponent({
    name: "TagTermComponent",
    components: {
      TagNameItem,
      ListItem, TagItem, FramedListItem, CommonList, DropdownInput, SearchComponent},
    mixins: [pathMixin, errorMixin],
    data() : {curPage: number, pageSize: number, data: TagData[], searchName: string, toSelect: TagName[]} {
      return {
        curPage: 1,
        pageSize: 5,
        data: [],
        searchName: '',
        toSelect: [],
      }
    },
    computed: {
      tagS: () => new TagService()
    },
    watch: {
      async searchName(newValue: string) {
        this.errorMsg = '';
        if (newValue.length < 2)
          this.toSelect = [];
        else {
          const query = `tag_search_name=${newValue}`
          let response = await this.tagS.getTagsByName(query);
          if (!response.tags.some(tag => tag.name === newValue))
              response.tags.push({name: newValue});
          this.toSelect = response.tags;
        }
      }
    },
    props: {
      termId: {
        type: Number,
        required: true
      }
    },
    async created() {
      await this.searchTags(this.curPage);
    },
    methods: {
      async searchTags(page: number) {
        this.curPage = page;

        const query = `rated_tag_search_amount=${this.pageSize}&rated_tag_search_page=${this.curPage}`;
        const response = await this.tagS.getTagsByTerm(this.termId, query);
        this.data = response.tags;
      },
      async addTag() {
        if (!store.state.auth.isAuth)
          this.errorMsg = "Unauthorized";
        else {
          const response = await this.tagS.addTagToTerm(this.termId, this.searchName);
          if (typeof response !== 'boolean')
            this.errorMsg = response.error;
          else
            this.searchName = '';
        }
      },
      choose(index: number) {
        this.searchName = this.toSelect[index].name;
      }
    }
  })
</script>

<style scoped>
  .tags {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .tag-label {
    display: flex;
    justify-content: center;
    width: 40%;
  }
  .tag-header {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 10px 0;
  }
  .tag-searcher {
    margin: 20px 0;
  }
</style>