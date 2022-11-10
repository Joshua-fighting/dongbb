<template>
  <div class="container">
    <div class="forms-container">
      <div class="signin-signup">
        <form action="" class="sign-in-form">
          <h2 class="title">DongBB</h2>
          <h2 class="title">前后端分离权限管理系统</h2>
          <div class="input-field">
            <i class="fa fa-user"></i>
            <input type="text" v-model="loginForm.username" placeholder="Username">
          </div>
          <div class="input-field">
            <i class="fa fa-lock"></i>
            <input type="password"  v-model="loginForm.password" placeholder="Password">
          </div>
          <div v-show="loginForm.errorVisible">
          {{loginForm.errorMsg}}
          </div>

          <el-row :gutter="20">
            <el-col :span="12">
              <input type="button" @click="userpwdLogin" value="登 录" class="btn solid">
            </el-col>
            <el-col :span="12">
              <input type="button" @click="dialogVisible = true" value="获取密码"  class="btn solid"/>
            </el-col>
          </el-row>

        </form>

        <form action="" class="sign-up-form">
          <h2 class="title">用户注册</h2>
          <div class="input-field">
            <i class="fa fa-user"></i>
            <input type="text" placeholder="Username">
          </div>
          <div class="input-field">
            <i class="fa fa-envelope"></i>
            <input type="text" placeholder="Email">
          </div>
          <div class="input-field">
            <i class="fa fa-lock"></i>
            <input type="password" placeholder="password">
          </div>
          <input type="button" value="注 册" class="btn solid">


        </form>
      </div>

      <div class="panels-container">
        <div class="panel left-panel">
          <div class="content">
            <h3>新用户注册</h3>
            <p>DongBB 权限管理系统是一个使用Spring Boot、Spring Security(JWT)、Vue开发的前后端分离的基础权限框架.</p>
            <button class="btn transparent" id="sign-up-btn">去注册</button>
          </div>

          <img src="../assets/img/log.svg" class="image" alt="">
        </div>

        <div class="panel right-panel">
          <div class="content">
            <h3>已经注册过？</h3>
            <p>因为注册功能不具有普适性，所以目前只是一个demo页面。需要你根据自己的业务设计提交注册信息，并自己编码实现.</p>
            <button class="btn transparent" id="sign-in-btn">去登陆</button>
          </div>

          <img src="../assets/img/register.svg" class="image" alt="">
        </div>
      </div>
    </div>

    <el-dialog
      title="扫描二维码"
      :visible.sync="dialogVisible"
      width="20%">
      <span>关注公众号，回复'dongbb'，字母哥所有资源均在此首发</span>
      <img src="../assets/img/wx-zimug-ketang.png" style="height: 200px;">
      <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogVisible = false">关闭</el-button>
          </span>
    </el-dialog>
  </div>


</template>

<script>
    import {setJwtToken} from '@/lib/utils'
    import {login} from '@/api/system/sys_user'
    export default {
        name: "login",
        data() {
            return {
                loginForm: {
                    username: "",
                    password: "",
                    errorMsg:"",
                    errorVisible: false
                },
                dialogVisible: false
            };
        },
       mounted(){
          const sign_in_btn = document.querySelector("#sign-in-btn");
          const sign_up_btn = document.querySelector("#sign-up-btn");
          const container = document.querySelector(".container");

          sign_up_btn.addEventListener('click',()=>{
            container.classList.add("sign-up-mode");
          })

          sign_in_btn.addEventListener('click',()=>{
            container.classList.remove("sign-up-mode");
          })
        },
        methods:{
            userpwdLogin(){
                login(this.loginForm.username,
                    this.loginForm.password
                ).then(res =>{
                    setJwtToken(res.data)
                    this.$router.push({path:"/home"})
                }).catch(err => {
                    this.loginForm.errorMsg = err.message;
                    this.loginForm.errorVisible = true;
                });
            },
            getPassword(){

            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  /* google字体库 */
  @import url("https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700;800&display=swap");

  *{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body, input{
    font-family: 'Poppins',sans-serif;
  }

  .container{
    position: relative;
    width: 100%;
    min-height:100vh;
    background-color: #fff;
    overflow: hidden;
  }

  .container::before{
    content: '';
    position: absolute;
    width: 2000px;
    height: 2000px;
    border-radius: 50%;
    background: linear-gradient(-45deg,#4481eb,#04befe);
    top: -10%;
    right: 48%;
    transform: translateY(-50%);
    z-index: 6;
    transition: 1.8s ease-in-out;
  }

  .forms-container{
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;

  }

  .signin-signup{
    position: absolute;
    top: 50%;
    left: 75%;
    /* 作用是什么 */
    transform: translate(-50%,-50%);
    width: 50%;
    display: grid;
    grid-template-columns: 1fr;
    z-index: 5;
    transition: 1s 0.7s ease-in-out;
  }

  form{
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 0 5rem;
    overflow: hidden;
    /* 难点 */
    grid-column: 1 / 2;
    grid-row: 1 / 2;

    /* delay防止表单提前暴露 */
    transition: 0.2s 0.7s ease-in-out;
  }

  form.sign-in-form{
    z-index: 2;
  }

  form.sign-up-form{
    z-index: 1;
    opacity: 0;
  }

  .title{
    font-size: 2.2rem;
    color: #444;
    margin-bottom: 10px;
  }

  .input-field{
    max-width: 300px;
    width: 100%;
    height: 55px;
    background-color: #f0f0f0;
    margin: 10px 0;
    border-radius: 55px;
    display: grid;
    grid-template-columns: 15% 85%;
    padding: 0 .4rem;
  }

  .input-field i{
    text-align: center;
    line-height: 55px;
    color: #acacac;
    font-size: 1.1rem;
  }

  .input-field input{
    background: none;
    outline: none;
    border: none;
    line-height: 1;
    font-weight: 600;
    font-size: 1.1rem;
    color: #333;
  }

  .input-field input::placeholder{
    color: #aaa;
    font-weight: 500;
  }

  .btn{
    width: 150px;
    height: 40px;
    border: none;
    outline: none;
    border-radius: 49px;
    cursor: pointer;
    background-color: #5995fd;
    color: #fff;
    /* 大写 */
    text-transform: uppercase;
    font-weight: 600;
    margin: 10xp 0;
    /* 动画延迟 */
    transition: .5s;
  }

  .btn:hover{
    background-color: #4d84e2;
  }

  .social-text{
    padding: .7rem 0;
    font-size: 1rem;
  }

  .social-media{
    display: flex;
    justify-content: center;
  }

  .social-icon{
    height: 40px;
    width: 40px;
    border: 1px solid #333;
    margin: 0 0.45rem;
    display: flex;
    justify-content: center;
    align-items: center;
    text-decoration: none;
    color: #333;
    font-size: 1.1rem;
    border-radius: 50%;
    transition: 0.3s;
  }

  .social-icon:hover{
    color: #4481eb;
    border-color: #44b1eb;
  }

  .panels-container{
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    display: grid;
    grid-template-columns: repeat(2,1fr);
  }

  .panel{
    display: flex;
    flex-direction:column;
    align-items: flex-end;
    text-align: center;
    justify-content: center;
    z-index: 7;
  }

  .panel .content{
    color: #fff;
    transition: .9s .6s ease-in-out;
  }

  .panel h3{
    font-weight: 600;
    line-height: 1;
    font-size: 1.5rem;
  }

  .panel p{
    font-size: 0.95rem;
    padding: 0.7rem 0;
  }

  .btn.transparent{
    margin: 0;
    background: none;
    border: 2px solid #fff;
    width: 130px;
    height: 41px;
    font-weight: 600;
    font-size: 0.8rem;
  }

  .left-panel{
    pointer-events: all;
    padding: 3rem 17% 2rem 12%;
  }

  .right-panel{
    pointer-events: none;
    padding: 3rem 12% 2rem 17%;
  }

  .image{
    width: 100%;
    transition: 1.1s .4s ease-in-out;
  }

  .right-panel .content, .right-panel .image{
    transform: translateX(800px);
  }

  .container.sign-up-mode:before{
    transform: translate(100%,-50%);
    right: 52%;
  }

  .container.sign-up-mode .left-panel .image,
  .container.sign-up-mode .left-panel .content
  {
    transform: translateX(-800px);
  }

  .container.sign-up-mode .right-panel .content,
  .container.sign-up-mode .right-panel .image{
    transform: translateX(0px);
  }

  .container.sign-up-mode .left-panel {
    pointer-events: none;
  }

  .container.sign-up-mode .right-panel{
    pointer-events: all;
  }

  .container.sign-up-mode .signin-signup{
    left: 25%;
  }

  .container.sign-up-mode form.sign-in-form{
    z-index: 1;
    opacity: 0;
  }

  .container.sign-up-mode form.sign-up-form{
    z-index: 2;
    opacity: 1;
  }

  @media(max-width: 870px){
    .container{
      min-height: 800px;
      height: 100vh;

    }

    .container:before{
      width: 1500px;
      height: 1500px;
      left: 30%;
      bottom: 68%;
      transform: translateX(-50%);
      right: initial;
      top: initial;
      transition: 2s ease-in-out;
    }

    .signin-signup{
      width: 100%;
      left: 50%;
      top: 85%;
      transform: translate(-50%,-100%);
      transition: 1s 0.8s ease-in-out;
    }

    .panels-container{

      z-index: 10;
      grid-template-columns: 1fr;
      grid-template-rows: 1fr 2fr 1fr;
    }

    .panel{
      flex-direction: row;
      justify-content: space-around;
      align-items: center;
      padding: 2.5rem 8%;
    }

    .image{
      width: 200px;
      transition: 0.9s 0.6s ease-in-out;
    }

    .left-panel{
      grid-row: 1 / 2;
    }

    .right-panel{
      grid-row: 3 / 4;
    }

    .panel h3{
      font-size: 1.2rem;
    }

    .panel p{
      font-size: 0.7rem;
      padding: 0.5rem 0;
    }

    .btn.btn.transparent{
      width: 110px;
      height: 35px;
      font-variation-settings: 0.7rem;
    }

    .panel .content{
      padding-right: 15%;
      transition: 0.9s 0.8s ease-in-out;
    }

    .right-panel .content, .right-panel .image{
      transform: translateY(300px);
    }

    .container.sign-up-mode:before{
      transform: translate(-50%, 100%);
      bottom: 32%;
      right: initial;
    }

    .container.sign-up-mode .left-panel .image,
    .container.sign-up-mode .left-panel .content{
      transform: translateY(-300px);
    }

    .container.sign-up-mode .signin-signup{
      top: 5%;
      transform: translate(-50%,0);
      left: 50%;
    }
  }

  @media (max-width: 465px){
    form{
      padding: 0 1.5rem;
    }

    .image{
      display: none;
    }

    .container .left-panel .content,
    .container .right-panel .content
    {
      margin-left: 40px;
    }

  }
</style>