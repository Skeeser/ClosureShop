<template>
  <!-- 通过类名，用css定义各种样式 -->
  <div class="login" :style="'background-image:url(' + Background + ');'">
    <!-- el-form element的组件 表单 -->
    <!-- model表示数据绑定，填写的数据都会自动同步到model对象上
        rules表示验证规则， prop指定具体的对应的规则
    -->
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      v-if="!registerBoxVisible"
      label-position="left"
      label-width="0px"
      class="login-form"
    >
      <h3 class="title">Closure电商平台</h3>
      <el-form-item prop="username">
        <!-- 组件的文本的输入框 -->
        <!-- v-model会绑定到model的某个具体属性中 -->
        <el-input
          v-model="loginForm.username"
          type="text"
          auto-complete="off"
          placeholder="账号"
          prefix-icon="iconfont icon-user"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <!-- type password 表示密码形式，会帮你变成隐藏形式 -->
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          prefix-icon="iconfont icon-3702mima"
          @keyup.enter.native="handleLogin"
        >
        </el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button
          size="medium"
          type="primary"
          style="width: 48%"
          @click.native.prevent="handleLogin"
        >
          <span>登 录</span>
        </el-button>
        <el-button
          size="medium"
          type="primary"
          style="width: 48%"
          @click="registerBoxVisible = true"
        >
          <span>注 册</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!-- 注册窗体 -->
    <el-form
      :model="addUserForm"
      ref="addUserFormRef"
      v-if="registerBoxVisible"
      :rules="addUserFormRules"
      label-position="left"
      label-width="70px"
      class="login-form"
    >
      <h3 class="title">注册</h3>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="addUserForm.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="addUserForm.password"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="addUserForm.email"></el-input>
      </el-form-item>
      <el-form-item label="手机" prop="mobile">
        <el-input v-model="addUserForm.mobile"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button
          size="medium"
          type="primary"
          style="width: 48%"
          @click.native.prevent="handleRegister"
        >
          <span>确 定</span>
        </el-button>
        <el-button
          size="medium"
          type="primary"
          style="width: 48%"
          @click="registerBoxVisible = false"
        >
          <span>取 消</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Background from '@/assets/images/background_arknights01.jpg'

// 下面是具体的行为区
export default {
  name: 'Login',
  data() {
    // 自定义邮箱规则, 正则表达式
    var checkEmail = (rule, value, callback) => {
      const regEmail = /^\w+@\w+(\.\w+)+$/
      if (regEmail.test(value)) {
        // 合法邮箱
        return callback()
      }
      callback(new Error('请输入合法邮箱'))
    }
    // 自定义手机号规则
    var checkMobile = (rule, value, callback) => {
      const regMobile = /^1[34578]\d{9}$/
      if (regMobile.test(value)) {
        return callback()
      }
      // 返回一个错误提示
      callback(new Error('请输入合法的手机号码'))
    }
    // 返回数据对象
    return {
      Background: Background,
      // 注册窗口显示
      registerBoxVisible: false,
      // 用户u注册的表格对象
      addUserForm: {
        username: '',
        password: '',
        email: '',
        mobile: ''
      },
      // 用户注册表单验证规则
      addUserFormRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 2,
            max: 10,
            message: '用户名的长度在2～10个字',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          {
            min: 6,
            max: 18,
            message: '用户密码的长度在6～18个字',
            trigger: 'blur'
          }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkMobile, trigger: 'blur' }
        ]
      },
      // 数据绑定返回的的表单对象
      loginForm: {
        username: 'admin',
        password: '123456'
      },
      // 具体的验证规则的定义
      // blur鼠标离开触发验证
      loginRules: {
        username: [
          { required: true, trigger: 'blur', message: '用户名不能为空' }
        ],
        password: [{ required: true, trigger: 'blur', message: '密码不能为空' }]
      }
    }
  },
  created() {
    document.title = '登录页面'
  },
  methods: {
    // 登录的处理函数
    handleLogin() {
      // 数据预验证
      // ref 表单引用对象  vaild验证结果，是一个布尔值
      this.$refs.loginForm.validate(async (valid) => {
        // 验证通过，发起请求
        if (valid) {
          // console.log('验证通过')
          // this.$http.post('login', this.loginForm): 返回值为promise
          // 返回值为promise，可加await简化操作 相应的也要加async
          const { data: res } = await this.$http.post('login', this.loginForm)
          console.log(res)
          if (res.meta.status !== 200) return this.$message.error('登录失败')
          this.$message.success('登录成功')
          // 1、将登陆成功之后的token, 保存到客户端的sessionStorage中; localStorage中是持久化的保存
          //   1.1 项目中出现了登录之外的其他API接口，必须在登陆之后才能访问
          //   1.2 token 只应在当前网站打开期间生效，所以将token保存在sessionStorage中
          window.sessionStorage.setItem('token', res.data.token)
          // 2、通过编程式导航跳转到后台主页, 路由地址为：/home
          this.$router.push('/home')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 注册的处理函数
    handleRegister() {
      // 提交请求前，表单预验证
      this.$refs.addUserFormRef.validate(async (valid) => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.post('users', this.addUserForm)
        console.log(res)
        if (res.meta.status !== 201) {
          this.$message.error('注册失败, 可能当前用户名已被占用！')
          return
        }
        this.$message.success('注册成功！')
        this.registerBoxVisible = false
        this.loginForm.username = this.addUserForm.username
        this.loginForm.password = this.addUserForm.password
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
// .是类选择器
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-size: cover;
}
.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: rgba(255, 255, 255, 0.8);
  width: 385px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  display: inline-block;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
</style>
