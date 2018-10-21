<template>
  <div id="recipes">
    <PostCard />
    <PostCard />
    <PostCard />
    <PostCard />
    <PostCard />
    <PostCard />
    <PostCard />
  </div>
</template>

<style lang="scss">
#recipes {
  display: grid;
  grid-template-columns: repeat(auto-fit, 500px);
  grid-template-rows: auto;
  grid-gap: 1rem;
  padding: 1rem;
  justify-content: center;
}
</style>

<script>
import PostCard from '@/components/cards/post'

export default {
  name: 'CategoryPage',
  components: {
    PostCard
  }
}
</script>
