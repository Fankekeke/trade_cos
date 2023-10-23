<template>
  <a-modal v-model="show" title="订单评价" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
        <a-row style="padding-left: 24px;padding-right: 24px;">
            <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
            <a-col :span="8"><b>订单编号：</b>
              {{ orderData.code }}
            </a-col>
            <a-col :span="8"><b>下单时间：</b>
              {{ orderData.createDate ? orderData.createDate : '- -' }}
            </a-col>
            <a-col :span="8"><b>订单价格：</b>
              {{ orderData.totalPrice ? orderData.totalPrice : '- -' }}
            </a-col>
        </a-row>
        <br/>
        <a-row style="padding-left: 24px;padding-right: 24px;">
            <a-col :span="8"><b>当前状态：</b>
            <span v-if="orderData.orderStatus == 0">待付款</span>
            <span v-if="orderData.orderStatus == 1">已下单</span>
            <span v-if="orderData.orderStatus == 2">配送中</span>
            <span v-if="orderData.orderStatus == 3">已收货</span>
            </a-col>
            <a-col :span="8"><b>订单金额：</b>
            {{ orderData.totalCost }} 元
            </a-col>
            <a-col :span="8"><b>下单时间：</b>
            {{ orderData.createDate }}
            </a-col>
        </a-row>
        <br/>
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col :span="8"><b>当前状态：</b>
            <span v-if="orderData.payStatus == 0">未支付</span>
            <span v-if="orderData.payStatus == 1">已支付</span>
            <span v-if="orderData.payStatus == 2">正在发货</span>
            <span v-if="orderData.payStatus == 3">已发货</span>
            <span v-if="orderData.payStatus == 4">已收货</span>
          </a-col>
          <a-col :span="8"><b>订单金额：</b>
            {{ orderData.totalCost }} 元
          </a-col>
          <a-col :span="8"><b>下单时间：</b>
            {{ orderData.createDate }}
          </a-col>
        </a-row>
        <br/>
        
        <br/>
    </div>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='评价分数' v-bind="formItemLayout">
            <a-rate v-decorator="[
            'score',
            { rules: [{ required: true, message: '请输入评价分数!' }] }
            ]" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='评价内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='图册' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
              @change="picHandleChange"
            >
              <div v-if="fileList.length < 8">
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
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
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
  name: 'evaluateAdd',
  props: {
    evaluateAddVisiable: {
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.evaluateAddVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      durgList: []
    }
  },
  watch: {
    evaluateAddVisiable: function (value) {
      if (value) {
        this.dataInit(this.orderData.id)
      }
    }
  },
  methods: {
    dataInit (orderId) {
      // 药品信息
      this.$get(`/cos/order-detail/detail/${orderId}`).then((r) => {
        this.durgList = r.data.data
      })
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
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        images.push(image.response)
      })
      this.form.validateFields((err, values) => {
        values.orderId = this.orderData.id
        values.userId = this.orderData.userId
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          values.publisher = this.currentUser.userId
          this.loading = true
          this.$post('/cos/evaluate-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
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
