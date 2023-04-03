import { defineComponent } from 'vue';

export default defineComponent({
    methods: {
        getQuery(): string {
            return this.$route.fullPath.substring(this.$route.path.length + 1);
        }
    },
});