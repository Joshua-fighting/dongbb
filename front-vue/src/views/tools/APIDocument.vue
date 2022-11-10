<template>
  <iframe :src="`${backendServer}/swagger-ui/`"
          class="db-document-frame"/>
</template>

<script>
    import {backendServer} from '@/api/index'
    export default {
      name: "APIDocument",
      data() {
        return {
          publicPath: process.env.BASE_URL,
          backendServer: backendServer
        }
      },
    }
</script>

<style scoped>
  .db-document-frame{
    margin-top: 40px;
    background-color:transparent;
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left:0;
  }

</style>