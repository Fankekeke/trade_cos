<template>
  <a-card :bordered="false" hoverable style="margin-top: 10px;background-color: #f9f9f9">
    <div style="text-align: left;font-size: 14px;margin-bottom: 30px"><b>二手交易平台</b></div>
    <div class="login">
      <a-form @submit.prevent="doLogin" :autoFormCreate="(form) => this.form = form">
        <div>
          <a-alert type="error" :closable="true" v-show="error" :message="error" showIcon
                   style="margin-bottom: 24px;"></a-alert>
          <a-form-item
            fieldDecoratorId="name"
            :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入账户名', whitespace: true}]}">
            <a-input size="large">
              <a-icon slot="prefix" type="user"></a-icon>
            </a-input>
          </a-form-item>
          <a-form-item
            fieldDecoratorId="password"
            :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入密码', whitespace: true}]}">
            <a-input size="large" type="password">
              <a-icon slot="prefix" type="lock"></a-icon>
            </a-input>
          </a-form-item>
          <a-row :gutter="16">
            <a-col class="gutter-row" :span="8" @click="refreshIdentifyCode">
              <s-identify :identifyCode="identifyCode"></s-identify>
            </a-col>
            <a-col class="gutter-row" :span="14" :offset="2">
              <a-input v-model="identifyCodeCheck"></a-input>
            </a-col>
          </a-row>
        </div>
        <a-form-item>
          <a-button :loading="loading" style="width: 100%; margin-top: 4px" size="large" htmlType="submit" type="primary">
            登录
          </a-button>
        </a-form-item>
        <div>
          <a style="float: right" @click="regist">注册账户</a>
        </div>
      </a-form>
    </div>
  </a-card>
</template>

<script>
import {mapMutations} from 'vuex'
import SIdentify from './SIdentify'

export default {
  name: 'Login',
  components: {SIdentify},
  data () {
    return {
      loading: false,
      error: '',
      activeKey: '1',
      identifyCode: '',
      identifyCodeCheck: '',
      identifyCodes: '1234567890abcdefghizklmnopqrstuvwxyz'
    }
  },
  computed: {
    systemName () {
      return this.$store.state.setting.systemName
    },
    copyright () {
      return this.$store.state.setting.copyright
    }
  },
  created () {
    this.$db.clear()
    this.$router.options.routes = []
    // 初始化验证码
    this.identifyCode = ''
    this.makeIdentifyCode(4)
  },
  methods: {
    // 刷新验证码
    refreshIdentifyCode () {
      this.identifyCode = ''
      this.makeIdentifyCode(4)
    },
    // 生成4位数的随机验证码
    makeIdentifyCode (l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode +=
          this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
      }
    },
    // 生成单个验证码
    randomNum (min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },
    doLogin () {
      if (this.activeKey === '1') {
        if (this.identifyCodeCheck === '') {
          this.$message.error('请输入验证码！')
          return false
        }
        if (this.identifyCodeCheck !== this.identifyCode) {
          this.$message.error('验证码错误！')
          this.identifyCodeCheck = ''
          this.refreshIdentifyCode()
          return false
        }
        // 用户名密码登录
        this.form.validateFields(['name', 'password'], (errors, values) => {
          if (!errors) {
            this.loading = true
            let name = this.form.getFieldValue('name')
            let password = this.form.getFieldValue('password')
            this.$post('login', {
              username: name,
              password: password
            }).then((r) => {
              let data = r.data.data
              this.saveLoginData(data)
              setTimeout(() => {
                this.loading = false
              }, 500)
              this.$router.push('/')
            }).catch((e) => {
              console.error(e)
              setTimeout(() => {
                this.loading = false
              }, 500)
            })
          }
        })
      }
      if (this.activeKey === '2') {
        // 手机验证码登录
        this.$message.warning('暂未开发')
      }
    },
    regist () {
      this.$emit('regist', 'Regist')
    },
    getCaptcha () {
      this.$message.warning('暂未开发')
    },
    handleTabsChange (val) {
      this.activeKey = val
    },
    ...mapMutations({
      setToken: 'account/setToken',
      setExpireTime: 'account/setExpireTime',
      setPermissions: 'account/setPermissions',
      setRoles: 'account/setRoles',
      setUser: 'account/setUser',
      setTheme: 'setting/setTheme',
      setLayout: 'setting/setLayout',
      setMultipage: 'setting/setMultipage',
      fixSiderbar: 'setting/fixSiderbar',
      fixHeader: 'setting/fixHeader',
      setColor: 'setting/setColor'
    }),
    saveLoginData (data) {
      this.setToken(data.token)
      this.setExpireTime(data.exipreTime)
      this.setUser(data.user)
      this.setPermissions(data.permissions)
      this.setRoles(data.roles)
      this.setTheme(data.config.theme)
      this.setLayout(data.config.layout)
      this.setMultipage(data.config.multiPage === '1')
      this.fixSiderbar(data.config.fixSiderbar === '1')
      this.fixHeader(data.config.fixHeader === '1')
      this.setColor(data.config.color)
    }
  }
}
</script>

<style lang="less" scoped>
  .login {
    .icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }
  }
</style>
