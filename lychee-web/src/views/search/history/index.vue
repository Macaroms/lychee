<template>
  <page-header-wrapper :title='false'>
    <a-list
      rowKey="id"
      :grid="{gutter: 24, lg: 3, md: 2, sm: 1, xs: 1}"
      :dataSource="list"
      class="card-list"
      :loading="loading"
    >
      <a-list-item slot="renderItem" slot-scope="item">
        <template>
          <a-card style="height: 120px" :hoverable="true" @click="viewPage(item.link)">
            <a-card-meta>
              <a slot="title">{{ item.year + day }}</a>
              <div class="meta-content" slot="description">{{ item.title }}</div>
            </a-card-meta>
          </a-card>
        </template>
      </a-list-item>
    </a-list>
  </page-header-wrapper>
</template>

<script>
import {getAction} from '@/api/httpManager.js'

export default {
  name: 'History',
  data() {
    return {
      loading: false,
      url: {
        history: '/api/text/history',
      },
      day: '',
      list: []
    }
  },
  mounted() {
    this.history()
  },
  methods: {
    history() {
      this.loading = true
      let today = new Date();
      this.day= "/" + (today .getMonth()+1) + "/" + today.getDate();
      getAction(this.url.history).then((res) => {
        if(res.code === 200){
          this.list = res.data
        }else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.loading = false
      })
    },
    viewPage(link) {
      window.open(link, '_blank');
    }
  }
}
</script>

<style lang="less" scoped>
@import "~@/components/index.less";

.card-list {
  /deep/ .ant-card-body:hover {
    .ant-card-meta-title > a {
      color: @primary-color;
    }
  }

  /deep/ .ant-card-meta-title {
    margin-bottom: 12px;

    & > a {
      display: inline-block;
      max-width: 100%;
      color: rgba(0, 0, 0, .85);
    }
  }

  /deep/ .meta-content {
    position: relative;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    height: 64px;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;

    margin-bottom: 1em;
  }
}

</style>
