<template>
  <div class="rating" :title="authorized ? 'Rating' : 'Authorize'">
    <i
        v-for="star in 5"
        :key="star"
        class="star"
        :class="{ 'filled': star <= initialRating && authorized, 'inactive': !authorized }"
        @click="setRating(star)"
    >&#10032;</i>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'MyRating',
  props: {
    initialRating: {
      type: Number,
      default: 0,
      validator: (value: number) => value >= 0 && value <= 5,
    },
    authorized: {
      type: Boolean,
      required: true
    }
  },
  emits: ['setRating'],
  methods: {
    setRating(rating: number) {
      this.$emit('setRating', rating);
    },
  },
});
</script>

<style scoped>
  .rating {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }

  .star {
    font-size: 2rem;
    color: black;
    cursor: pointer;
  }

  .filled {
    color: gold;
    font-weight: bolder;
  }

  .inactive {
    color: gray;
    font-weight: bolder;
  }
</style>