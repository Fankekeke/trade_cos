<template>
  <a-row :gutter="8" style="width: 100%">
    <a-col :span="6">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <b style="font-size: 15px">我的信息</b>
        </a-card>
        <a-card :bordered="false">
          <a-form :form="form" layout="vertical">
            <a-row :gutter="20">
              <a-col :span="24">
                <a-form-item label='客户姓名' v-bind="formItemLayout">
                  <a-input disabled v-decorator="[
                  'name',
                  { rules: [{ required: true, message: '请输入客户姓名!' }] }
                  ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='联系电话' v-bind="formItemLayout">
                  <a-input v-decorator="[
                  'phone',
                  { rules: [{ required: true, message: '请输入联系电话!' }] }
                  ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='电子邮箱' v-bind="formItemLayout">
                  <a-input v-decorator="[
                  'mail',
                  { rules: [{ required: true, message: '请输入电子邮箱!' }] }
                  ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='收获地址' v-bind="formItemLayout">
                  <a-input v-decorator="[
                  'address',
                  { rules: [{ required: true, message: '请输入收获地址!' }] }
                  ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='城市' v-bind="formItemLayout">
                  <a-input v-decorator="[
                  'city',
                  { rules: [{ required: true, message: '请输入城市!' }] }
                  ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='区域' v-bind="formItemLayout">
                  <a-input v-decorator="[
                  'area',
                  { rules: [{ required: true, message: '请输入区域!' }] }
                  ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='省份' v-bind="formItemLayout">
                  <a-input v-decorator="[
                  'province',
                  { rules: [{ required: true, message: '请输入省份!' }] }
                  ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='头像' v-bind="formItemLayout">
                  <a-upload
                    name="avatar"
                    action="http://127.0.0.1:9527/file/fileUpload/"
                    list-type="picture-card"
                    :file-list="fileList"
                    @preview="handlePreview"
                    @change="picHandleChange"
                  >
                    <div v-if="fileList.length < 1">
                      <a-icon type="plus" />
                      <div class="ant-upload-text">
                        Upload
                      </div>
                    </div>
                  </a-upload>
                  <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
                    <img alt="example" style="width: 100%" :src="previewImage" />
                  </a-modal>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item>
                  <a-button
                    size="large"
                    type="primary"
                    htmlType="submit"
                    class="register-button"
                    style="width: 35%;float: right;margin-top: 50px"
                    :loading="loading"
                    @click.stop.prevent="handleSubmit"
                    :disabled="loading">立即修改
                  </a-button>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-card>
      </div>
    </a-col>
    <a-col :span="18">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <a-spin :spinning="dataLoading">
            <a-calendar>
              <ul slot="dateCellRender" slot-scope="value" class="events">
                <li v-for="item in getListData(value)" :key="item.content">
                  <a-badge :status="item.type" :text="item.content" />
                </li>
              </ul>
            </a-calendar>
          </a-spin>
        </a-card>
      </div>
    </a-col>
  </a-row>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'User',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      courseInfo: [],
      dataLoading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  mounted () {
    this.dataInit()
  },
  methods: {
    isDuringDate (beginDateStr, endDateStr, curDataStr) {
      let curDate = new Date(curDataStr)
      let beginDate = new Date(beginDateStr)
      let endDate = new Date(endDateStr)
      if (curDate >= beginDate && curDate <= endDate) {
        return true
      }
      return false
    },
    getListData (value) {
      let listData = []
      this.courseInfo.forEach(item => {
        if ((moment(value).format('YYYY-MM-DD')) === (moment(item.createDate).format('YYYY-MM-DD'))) {
          listData.push({type: 'none', content: item.code})
        }
      })
      return listData || []
    },
    dataInit () {
      this.dataLoading = true
      this.$get(`/cos/user-info/detail/${this.currentUser.userId}`).then((r) => {
        this.rowId = r.data.user.id
        this.setFormValues(r.data.user)
        this.courseInfo = r.data.order
        this.dataLoading = false
      })
    },
    setFormValues ({...student}) {
      this.rowId = student.id
      let fields = ['name', 'phone', 'address', 'province', 'city', 'area', 'mail']
      let obj = {}
      Object.keys(student).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = student[key]
        }
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(student['images'])
        }
      })
      this.form.setFieldsValue(obj)
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/cos/user-info', {
            ...values
          }).then((r) => {
            this.$message.success('修改信息成功')
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
