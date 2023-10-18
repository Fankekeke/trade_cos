<template>
  <div :class="[multipage === true ? 'multi-page':'single-page', 'not-menu-page', 'home-page']">
    <a-row :gutter="8" class="head-info">
      <a-card class="head-info-card">
        <a-skeleton active v-if="loading" />
        <a-col :span="12" v-if="!loading">
          <div class="head-info-avatar">
            <img alt="头像" :src="avatar">
          </div>
          <div class="head-info-count">
            <div class="head-info-welcome">
              {{welcomeMessage}}
            </div>
            <div class="head-info-desc">
              <p>{{user.roleName ? user.roleName : '暂无角色'}}</p>
            </div>
            <div class="head-info-time">上次登录时间：{{user.lastLoginTime ? user.lastLoginTime : '第一次访问系统'}}</div>
          </div>
        </a-col>
        <a-col :span="12" v-if="!loading">
          <div>
            <a-row class="more-info" v-if="user.roleId != 75 && stockInfo !== null">
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4">
                <head-info title="入库次数" :content="stockInfo.putCount" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="本月次数" :content="stockInfo.outCount" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="总消耗金额" :content="stockInfo.price" :center="false" />
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-card>
    </a-row>
    <a-row :gutter="15" class="count-info" v-if="user.roleId == 75">
      <a-col :span="8" class="project-wrapper" style="padding: 0">
        <a-card hoverable class="visit-count">
          <a-skeleton active v-if="loading" />
          <apexchart v-if="!loading" type="donut" height=250 :options="chartOptions5" :series="series5"></apexchart>
        </a-card>
      </a-col>
      <a-col :span="16">
        <a-card hoverable>
          <a-skeleton active v-if="loading" />
          <apexchart v-if="!loading" type="bar" height="280" :options="chartOptions6" :series="series6"></apexchart>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="15" class="count-info" style="margin-top: 15px">
      <a-col :span="12" class="project-wrapper" style="padding: 0">
        <a-card hoverable :loading="loading" title="公告信息">
          <div>
            <a-list item-layout="vertical" :pagination="pagination" :data-source="bulletinList">
              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                <template slot="actions">
              <span key="message">
                <a-icon type="message" style="margin-right: 8px" />
                {{ item.date }}
              </span>
                </template>
                <a-list-item-meta :description="item.content" style="font-size: 14px">
                  <a slot="title">{{ item.title }}</a>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </a-card>
        <a-row :gutter="8" style="margin-top: 15px">
          <a-col :span="12" v-if="user.roleId != 75">
            <a-card hoverable class="visit-count">
              <a-skeleton active v-if="loading" />
              <apexchart v-if="!loading" type="donut" height=250 :options="chartOptions3" :series="series3"></apexchart>
            </a-card>
          </a-col>
          <a-col :span="12" v-if="user.roleId != 75">
            <a-card hoverable class="visit-count">
              <a-skeleton active v-if="loading" />
              <apexchart v-if="!loading" type="donut" height=250 :options="chartOptions4" :series="series4"></apexchart>
            </a-card>
          </a-col>
          <a-col :span="24" class="visit-count-wrapper" v-show="user.roleId != 75" style="margin-top: 15px">
            <a-skeleton active v-if="loading" />
            <a-card v-show="!loading" hoverable class="visit-count">
              <apexchart ref="count" type=bar height=300 :options="chartOptions" :series="series" />
            </a-card>
          </a-col>
        </a-row>
      </a-col>
      <a-col :span="12" v-if="user.roleId == 75">
        <a-card hoverable :loading="loading" title="商铺信息">
          <a-form :form="form" layout="vertical">
            <a-row :gutter="20">
              <a-col :span="12">
                <a-form-item label='商铺名称' v-bind="formItemLayout">
                  <a-input v-decorator="[
                'name',
                { rules: [{ required: true, message: '请输入商铺名称!' }] }
                ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='采购小组' v-bind="formItemLayout">
                  <a-input v-decorator="[
                'team',
                { rules: [{ required: true, message: '请输入采购小组!' }] }
                ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='备注' v-bind="formItemLayout">
                  <a-textarea :rows="4" v-decorator="[
                'content'
                ]"/>
                </a-form-item>
              </a-col>
            </a-row>
            <a-button @click="handleSubmit" type="primary">修改</a-button>
          </a-form>
        </a-card>
      </a-col>
      <a-col :span="12" class="project-wrapper" v-if="user.roleId != 75">
        <a-row :gutter="15">
          <a-col>
            <a-card hoverable class="visit-count">
              <a-skeleton active v-if="loading" />
              <apexchart v-if="!loading" type="line" height="350" :options="chartOptions1" :series="series1"></apexchart>
            </a-card>
          </a-col>
          <a-col style="margin-top: 15px">
            <a-card hoverable class="visit-count">
              <a-skeleton active v-if="loading" />
              <apexchart v-if="!loading" type="line" height="350" :options="chartOptions2" :series="series2"></apexchart>
            </a-card>
          </a-col>
        </a-row>
      </a-col>
    </a-row>
  </div>
</template>
<script>
import HeadInfo from '@/views/common/HeadInfo'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'HomePage',
  components: {HeadInfo},
  data () {
    return {
      form: this.$form.createForm(this),
      formItemLayout,
      series6: [{
        name: '采购金额',
        data: []
      }],
      chartOptions6: {
        chart: {
          type: 'bar',
          height: 350
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        yaxis: {
          title: {
            text: '$ (thousands)'
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return '￥ ' + val + ' 元'
            }
          }
        }
      },
      series5: [],
      chartOptions5: {
        labels: [],
        chart: {
          type: 'donut'
        },
        title: {
          text: '采购类型统计',
          align: 'left'
        },
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      },
      series4: [44, 55, 41, 17, 15],
      chartOptions4: {
        labels: [],
        chart: {
          type: 'donut'
        },
        title: {
          text: '出库类型统计',
          align: 'left'
        },
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      },
      series3: [44, 55, 41, 17, 15],
      chartOptions3: {
        labels: [],
        chart: {
          type: 'donut'
        },
        title: {
          text: '入库类型统计',
          align: 'left'
        },
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      },
      series2: [{
        name: '出库数量',
        data: [10, 41, 35, 51, 49, 62, 69, 91, 148]
      }],
      chartOptions2: {
        chart: {
          height: 350,
          type: 'line',
          zoom: {
            enabled: false
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'straight'
        },
        title: {
          text: '出库统计',
          align: 'left'
        },
        grid: {
          row: {
            colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
            opacity: 0.5
          }
        },
        xaxis: {
          categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep']
        }
      },
      series1: [{
        name: '入库数量',
        data: [34, 44, 54, 21, 12, 43, 33, 23, 66, 66, 58]
      }],
      chartOptions1: {
        chart: {
          type: 'line',
          height: 350
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '入库统计',
          align: 'left'
        },
        xaxis: {
          categories: []
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series: [],
      chartOptions: {
        chart: {
          toolbar: {
            show: false
          }
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '35%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        fill: {
          opacity: 1

        }
      },
      todayIp: '',
      todayVisitCount: '',
      totalVisitCount: '',
      userRole: '',
      userDept: '',
      lastLoginTime: '',
      welcomeMessage: '',
      loading: false,
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 2
      },
      bulletinList: [],
      stockInfo: null,
      studentInfo: null
    }
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    }),
    avatar () {
      return `static/avatar/${this.user.avatar}`
    }
  },
  methods: {
    welcome () {
      const date = new Date()
      const hour = date.getHours()
      let time = hour < 6 ? '早上好' : (hour <= 11 ? '上午好' : (hour <= 13 ? '中午好' : (hour <= 18 ? '下午好' : '晚上好')))
      return `${time}，${this.user.username}`
    },
    setFormValues ({...combo}) {
      this.rowId = combo.id
      let fields = ['name', 'content', 'team']
      let obj = {}
      Object.keys(combo).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = combo[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          values.id = this.studentInfo.id
          this.$put('/cos/student-info', {
            ...values
          }).then((r) => {
            this.$message.success('修改成功')
          })
        }
      })
    },
    selectStatisticsByUserId (userId) {
      this.loading = true
      this.$get(`/cos/stock-out/stockOut/statistics/${userId}`).then((r) => {
        let data = r.data
        if (data.bar !== null) {
          let labels = []
          let values = []
          data.bar.forEach(e => {
            labels.push(e.days)
            values.push(e.orderPrice)
          })
          this.series6[0].data = values
          this.chartOptions6.xaxis.categories = labels
        }
        if (data.type !== null) {
          let labels = []
          let values = []
          Object.keys(data.type).forEach(e => {
            labels.push(e)
            values.push(data.type[e])
          })
          this.series5 = values
          this.chartOptions5.labels = labels
        }
        setTimeout(() => {
          this.loading = false
        }, 1000)
      })
    },
    home () {
      this.loading = true
      this.$get('/cos/stock-info/home', {type: this.user.roleId, userId: this.user.userId}).then((r) => {
        this.stockInfo = r.data.stockInfo
        this.studentInfo = r.data.studentInfo
        this.setFormValues(r.data.studentInfo)
        if (this.user.roleId.toString() === '75') {
          this.selectStatisticsByUserId(this.studentInfo.id)
        }
        this.bulletinList = r.data.bulletinList
        let stockOutTypeRateLabel = []
        let stockOutTypeRateData = []
        r.data.stockOutTypeRate.forEach(item => {
          stockOutTypeRateLabel.push(item.name)
          stockOutTypeRateData.push(item.price)
        })
        this.series4 = stockOutTypeRateData
        this.chartOptions4.labels = stockOutTypeRateLabel
        let stockPutTypeRateLabel = []
        let stockPutTypeRateData = []
        r.data.stockPutTypeRate.forEach(item => {
          stockPutTypeRateLabel.push(item.name)
          stockPutTypeRateData.push(item.price)
        })
        this.series3 = stockPutTypeRateData
        this.chartOptions3.labels = stockPutTypeRateLabel
        let stockPutRateLabel = []
        let stockPutRateData = []
        r.data.stockPutRate.forEach(item => {
          stockPutRateLabel.push(item.days)
          stockPutRateData.push(item.amount)
        })
        this.chartOptions1.xaxis.categories = stockPutRateLabel
        this.series1[0].data = stockPutRateData
        let stockOutRateLabel = []
        let stockOutRateData = []
        r.data.stockOutRate.forEach(item => {
          stockOutRateLabel.push(item.days)
          stockOutRateData.push(item.amount)
        })
        this.chartOptions2.xaxis.categories = stockOutRateLabel
        this.series2[0].data = stockOutRateData
        setTimeout(() => {
          this.loading = false
        }, 500)
      })
    }
  },
  mounted () {
    this.home()
    this.welcomeMessage = this.welcome()
    this.$get(`index/${this.user.username}`).then((r) => {
      let data = r.data.data
      this.todayIp = data.todayIp
      this.todayVisitCount = data.todayVisitCount
      this.totalVisitCount = data.totalVisitCount
      let sevenVisitCount = []
      let dateArr = []
      for (let i = 6; i >= 0; i--) {
        let time = moment().subtract(i, 'days').format('MM-DD')
        let contain = false
        for (let o of data.lastSevenVisitCount) {
          if (o.days === time) {
            contain = true
            sevenVisitCount.push(o.count)
          }
        }
        if (!contain) {
          sevenVisitCount.push(0)
        }
        dateArr.push(time)
      }
      let sevenUserVistCount = []
      for (let i = 6; i >= 0; i--) {
        let time = moment().subtract(i, 'days').format('MM-DD')
        let contain = false
        for (let o of data.lastSevenUserVisitCount) {
          if (o.days === time) {
            contain = true
            sevenUserVistCount.push(o.count)
          }
        }
        if (!contain) {
          sevenUserVistCount.push(0)
        }
      }
      this.$refs.count.updateSeries([
        {
          name: '您',
          data: sevenUserVistCount
        },
        {
          name: '总数',
          data: sevenVisitCount
        }
      ], true)
      this.$refs.count.updateOptions({
        xaxis: {
          categories: dateArr
        },
        title: {
          text: '近七日系统访问记录',
          align: 'left'
        }
      }, true, true)
    }).catch((r) => {
      console.error(r)
      this.$message.error('获取首页信息失败')
    })
  }
}
</script>
<style lang="less">
  .home-page {
    .head-info {
      margin-bottom: .5rem;
      .head-info-card {
        padding: .5rem;
        border-color: #f1f1f1;
        .head-info-avatar {
          display: inline-block;
          float: left;
          margin-right: 1rem;
          img {
            width: 5rem;
            border-radius: 2px;
          }
        }
        .head-info-count {
          display: inline-block;
          float: left;
          .head-info-welcome {
            font-size: 1.05rem;
            margin-bottom: .1rem;
          }
          .head-info-desc {
            color: rgba(0, 0, 0, 0.45);
            font-size: .8rem;
            padding: .2rem 0;
            p {
              margin-bottom: 0;
            }
          }
          .head-info-time {
            color: rgba(0, 0, 0, 0.45);
            font-size: .8rem;
            padding: .2rem 0;
          }
        }
      }
    }
    .count-info {
      .visit-count-wrapper {
        padding-left: 0 !important;
        .visit-count {
          padding: .5rem;
          border-color: #f1f1f1;
          .ant-card-body {
            padding: .5rem 1rem !important;
          }
        }
      }
      .project-wrapper {
        padding-right: 0 !important;
        .project-card {
          border: none !important;
          .ant-card-head {
            border-left: 1px solid #f1f1f1 !important;
            border-top: 1px solid #f1f1f1 !important;
            border-right: 1px solid #f1f1f1 !important;
          }
          .ant-card-body {
            padding: 0 !important;
            table {
              width: 100%;
              td {
                width: 50%;
                border: 1px solid #f1f1f1;
                padding: .6rem;
                .project-avatar-wrapper {
                  display:inline-block;
                  float:left;
                  margin-right:.7rem;
                  .project-avatar {
                    color: #42b983;
                    background-color: #d6f8b8;
                  }
                }
              }
            }
          }
          .project-detail {
            display:inline-block;
            float:left;
            text-align:left;
            width: 78%;
            .project-name {
              font-size:.9rem;
              margin-top:-2px;
              font-weight:600;
            }
            .project-desc {
              color:rgba(0, 0, 0, 0.45);
              p {
                margin-bottom:0;
                font-size:.6rem;
                white-space:normal;
              }
            }
          }
        }
      }
    }
  }
</style>
