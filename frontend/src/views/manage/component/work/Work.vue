<template>
  <div style="background:#ECECEC; padding:30px;margin-top: 30px;margin-bottom: 30px">
    <a-row :gutter="8" class="count-info">
      <a-card class="head-info-card" style="width: 65%;margin: 0 auto">
        <a-row>
          <a-col :span="24">
            <a-input-search placeholder="搜索贴子" v-show="!postDetailShow" style="width: 200px;margin-top: 10px;float: right" @search="onSearch" />
          </a-col>
        </a-row>
        <a-tabs :activeKey="tabKey" tab-position="top" @change="tabChange" v-show="!postDetailShow">
          <a-tab-pane v-for="item in tagList" :key="item.id" :tab="item.name">
            <a-skeleton active v-if="loading" />
            <div v-if="!loading" style="padding: 25px 80px">
              <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="postList">
                <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                  <template slot="actions">
                    <span key="message">
                      <a-icon type="message" style="margin-right: 8px" />
                      <span v-if="item.collect === 0">{{ item.reply }}</span>
                      <span v-else>{{ item.reply / item.collect }}</span> 回复
                    </span>
                    <span key="star">
                      <a-icon type="star" style="margin-right: 8px" />
                      {{ item.collect }} 收藏
                    </span>
                    <span key="to-top">
                      <a-icon type="to-top" style="margin-right: 8px" />
                      {{ timeFormat(item.createDate) }}
                    </span>
                  </template>
                  <a-list-item-meta :description="item.content.slice(0, 100) + '...'">
                    <a slot="title" @click="postReplyDetail(item)">{{ item.title }}</a>
                    <a-avatar shape="square" slot="avatar" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + item.userImages" />
                  </a-list-item-meta>
                </a-list-item>
              </a-list>
            </div>
          </a-tab-pane>
        </a-tabs>
        <div v-if="postDetailShow && postDetail !== null" style="margin: 18px">
          <div style="margin-bottom: 10px">
            <a-breadcrumb>
              <a-breadcrumb-item><a @click="postDetailShow = false">返回</a></a-breadcrumb-item>
              <a-breadcrumb-item>{{ tabName }}</a-breadcrumb-item>
            </a-breadcrumb>
          </div>
          <p style="font-size: 22px;color: black;font-weight: 500;line-height: 150%;margin: 25px 50px;margin-top: 50px">
            {{ postDetail.title }}
            <a-icon type="form" style="cursor: pointer" v-if="user.userId === postDetail.userId" @click="edit(postDetail)"/>
          </p>
          <div style="margin: 25px 50px;font-size: 13px">
            <a-icon v-if="collectUser === 0" type="heart" style="margin-right: 10px;cursor: pointer" @click="collectUserCheck(0)"/>
            <a-icon v-if="collectUser > 0" type="heart" style="margin-right: 10px;color: red;cursor: pointer" @click="collectUserCheck(1)"/>
            {{ postDetail.username }} 关注
            <a-divider type="vertical" />
            <a-icon type="eye" style="margin-right: 10px;margin-left: 40px" />
            {{ postDetail.pageviews }} 访问
            <a-divider type="vertical" />
            <a-icon type="message" style="margin-right: 10px" />
            <span v-if="postDetail.collect === 0">{{ postDetail.reply }}</span>
            <span v-else>{{ postDetail.reply / postDetail.collect }}</span> 回复
            <a-divider type="vertical" />
            <a-icon v-if="collectPost === 0" type="star" style="margin-right: 10px;cursor: pointer" @click="collectPostCheck(0)"/>
            <a-icon v-if="collectPost > 0" type="star" style="margin-right: 10px;color: red;cursor: pointer" @click="collectPostCheck(1)"/>
            {{ postDetail.collect }} 收藏
            <a-divider type="vertical" />
            {{ timeFormat(postDetail.createDate) }}
          </div>
          <div style="margin: 25px 50px;font-size: 15px;line-height: 1.6;word-break: break-word;letter-spacing: 1px;text-indent: 30px">
            {{ postDetail.content }}
          </div>
          <div style="margin: 25px 50px;height: 100px">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
            >
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </div>
          <div style="margin: 25px 50px;">
            <a-list
              class="comment-list"
              :pagination="pagination"
              :header="`${replyList.length} 回复`"
              item-layout="horizontal"
              :data-source="replyList"
            >
              <a-list-item slot="renderItem" slot-scope="item, index">
                <a-comment :author="item.username" shape="square" :avatar="'http://127.0.0.1:9527/imagesWeb/' + item.images">
                  <template slot="actions">
                    <span @click="replyUserAdd(item)">回复</span>
                  </template>
                  <p slot="content" style="white-space: pre-line;">
                    {{ item.content }}
                  </p>
                  <a-tooltip slot="datetime" :title="item.sendCreate">
                    <span>{{ timeFormat(item.sendCreate) }}</span>
                  </a-tooltip>
                </a-comment>
              </a-list-item>
            </a-list>
            <div style="margin-bottom: 200px;margin-top: 50px">
              <a-textarea
                v-model="replyContent"
                placeholder="Controlled autosize"
                :rows="5"
              />
              <a-button type="primary" style="float: right;margin-top: 15px" @click="commit">
                提交
              </a-button>
            </div>
          </div>
        </div>
      </a-card>
    </a-row>
  </div>
</template>

<script>

import {mapState} from 'vuex'
import VehicleView from './VehicleView.vue'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'Work',
  components: {VehicleView},
  data () {
    return {
      roomList: [],
      roomTypeList: [],
      loading: false,
      vehicleView: {
        visiable: false,
        data: null
      },
      rentView: {
        visiable: false,
        data: null
      },
      startDate: null,
      endDate: null
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  watch: {
    replyContent: function (value) {
      if (value === '') {
        this.replyUser = null
      }
    }
  },
  mounted () {
    this.getWorkStatusList()
  },
  methods: {
    getPostList (tagId) {
      this.loading = true
      this.$get(`/cos/post-info/tag/${tagId}`).then((r) => {
        this.postList = r.data.data
        setTimeout(() => {
          this.loading = false
        }, 500)
      })
    },
    getTagList () {
      this.$get('/cos/tag-info/list').then((r) => {
        this.tagList = [{id: -1, name: '推荐'}]
        this.tagList.push.apply(this.tagList, r.data.data)
        console.log(this.tagList)
        if (this.tagList.length !== 0) {
          this.tabChange(this.tagList[0].id)
        }
        let tagListData = []
        r.data.data.forEach(item => {
          tagListData.push({label: item.name, value: item.id})
        })
        this.tagListData = tagListData
      })
    },
    handlevehicleViewClose () {
      this.vehicleView.visiable = false
    },
    handlevehicleViewSuccess () {
      this.vehicleView.visiable = false
      this.$message.success('添加订单成功')
    },
    orderSuccess () {
      this.rentView.visiable = false
      this.$message.success('添加订单成功')
      this.fetch()
    },
    collectDelete (id) {
      this.$delete(`/cos/collect-info/${id}`).then((r) => {
        this.$message.success('取消收藏成功')
        this.fetch()
      })
    },
    collectAdd (roomCode) {
      let data = { userId: this.currentUser.userId, roomCode }
      this.$post(`/cos/collect-info`, data).then((r) => {
        this.$message.success('收藏成功')
        this.fetch()
      })
    },
    view (record) {
      if (this.startDate && this.endDate) {
        this.vehicleView.visiable = true
        this.vehicleView.data = record
      } else {
        this.$message.error('请选择租赁时间！')
      }
    },
    getRoomType () {
      this.$get(`/cos/vehicle-type-info/list`).then((r) => {
        this.roomTypeList = r.data.data
      })
    },
    getWorkStatusList (params) {
      if (params) {
        params.userId = this.currentUser.userId
        this.$get(`/cos/vehicle-info/order/check`, params).then((r) => {
          this.roomList = r.data.data
        })
      } else {
        this.$get(`/cos/vehicle-info/order/check`, { userId: this.currentUser.userId }).then((r) => {
          this.roomList = r.data.data
        })
      }
    },
    fetch () {
      if (this.startDate && this.endDate) {
        let params = { startDate: this.startDate, endDate: this.endDate }
        this.getWorkStatusList(params)
      } else {
        this.getWorkStatusList()
      }
    },
    onChange (date, dateString) {
      this.startDate = dateString[0]
      this.endDate = dateString[1]
      let params = { startDate: this.startDate, endDate: this.endDate }
      this.getWorkStatusList(params)
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 250px;
  line-height: 250px;
  overflow: hidden;
}

</style>
