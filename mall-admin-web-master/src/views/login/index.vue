<template>
  <div class="login-BigBox">

    <!-- 左上角的logo -->
    <div class="login-topLeft">
      <svg-icon icon-class="login-mall" style="width: 45px;height: 45px;color: #ff7701"></svg-icon>
      <h2>微信商城管理系统</h2>
    </div>
    <el-card class="login-form-layout">
      <el-form autoComplete="on"
               :model="loginForm"
               :rules="loginRules"
               ref="loginForm"
               label-position="left">
        <h2 class="login-title color-main">微信商城管理系统</h2>
        <el-form-item prop="username">
          <el-input name="username"
                    type="text"
                    v-model="loginForm.username"
                    autoComplete="on"
                    placeholder="请输入用户名"
                    clearable>
          <span slot="prefix">
            用&nbsp;户&nbsp;名
          </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input name="password"
                    :type="pwdType"
                    @keyup.enter.native="handleLogin"
                    v-model="loginForm.password"
                    autoComplete="on"
                    placeholder="请输入密码"
                    clearable>
          <span slot="prefix">
            登录密码
          </span>
            <span slot="suffix" @click="showPwd">
            <svg-icon icon-class="eye" style="color:#ff7701"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item style="margin: 70px -20px 0px">
          <el-button style="width: 100%" type="primary" :loading="loading" @click.native.prevent="handleLogin">
            登 &#x3000;录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>



    <div id="particles"></div>
  </div>
</template>

<script>
  import {isvalidUsername} from '@/utils/validate';
  import {setSupport,getSupport,SupportUrl} from '@/utils/support';
  import login_center_bg from '@/assets/images/login_center_bg.png'

  export default {
    name: 'login',
    data() {
      const validateUsername = (rule, value, callback) => {
        if (!isvalidUsername(value)) {
          callback(new Error('请输入正确的用户名'))
        } else {
          callback()
        }
      };
      const validatePass = (rule, value, callback) => {
        if (value.length < 3) {
          callback(new Error('密码不能小于3位'))
        } else {
          callback()
        }
      };
      return {
        loginForm: {
          username: 'admin',
          password: '123456',
        },
        loginRules: {
          username: [{required: true, trigger: 'blur', validator: validateUsername}],
          password: [{required: true, trigger: 'blur', validator: validatePass}]
        },
        loading: false,
        pwdType: 'password',
        login_center_bg,
        dialogVisible:false,
        supportDialogVisible:false
      }
    },
    mounted(){
      // 页面加载完成加载粒子组件
      $(document).ready(function() {
        $('#particles').particleground({
          dotColor: "#4c4e50",
          lineColor: "#4c4e50",
          maxSpeedX:0.7
        });
      });
    },
    methods: {
      showPwd() {
        if (this.pwdType === 'password') {
          this.pwdType = ''
        } else {
          this.pwdType = 'password'
        }
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {

            this.loading = true;
            this.$store.dispatch('Login', this.loginForm).then(() => {
              this.loading = false;
              this.$router.push({path: '/'})
            }).catch(() => {
              this.loading = false
            })
          } else {
            console.log('参数验证不合法！');
            return false
          }
        })
      },
      dialogConfirm(){
        this.dialogVisible =false;
        setSupport(true);
        // window.location.href=SupportUrl;
      },
      dialogCancel(){
        this.dialogVisible = false;
        setSupport(false);
      }
    }
  }
</script>

<style scoped lang="scss">
  
  .login-form-layout {
    border:none;
    width: 430px;
    z-index: 100;
    overflow: visible;
    border-radius: 8px;
  }

  .login-title {
    width: 350px;
    margin: 0px auto 50px -40px;
    font-weight: 400;
    font-size: 18px;
    background: #ff7701;
    padding: 20px;
    position: relative;
    color: white;
    &:before{
      position: absolute;
      bottom:-15px;
      left:0;
      content: '';
      border-top: 15px solid #b85601;
      border-left: 20px solid transparent;
    }
  }
  @media (max-width:440px){
    .login-form-layout{
      width: calc(100% - 50px);
    }
    .login-title{
      width: 280px;
    }
  }
</style>
