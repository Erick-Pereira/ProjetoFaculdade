<template>
  <div class="login-signup-container">
    <div :class="['alert', alertClass]" v-if="notificationMessage">{{ notificationMessage }}</div>
    <section class="wrapper" :class="{ active: isLogin }">
      <div class="form signup">
        <header @click="toggleForms">Cadastrar-se</header>
        <form @submit.prevent="signUpUser">
          <input type="text" placeholder="Nome completo" required v-model="signupData.nomeUsuario"/>
          <input type="text" placeholder="Complemento" required v-model="signupData.apartamentoUsuario"/>
          <input type="email" placeholder="E-mail" required v-model="signupData.emailUsuario"/>
          <input type="password" placeholder="Senha" required v-model="signupData.senhaUsuario" class="password-input" ref="signupPassword">
          <span class="password-signup" @click="togglePasswordVisibility('signup')" ref="signupPasswordToggle">
            <img src="/red-eyes.png" alt="Mostrar Senha">
          </span>
          <div class="checkbox">
            <input type="checkbox" id="signupCheck" v-model="acceptTerms"/>
            <label for="signupCheck">Eu aceito os <strong>Termos de uso</strong> e <strong>Política de privacidade</strong></label>
          </div>
          <input type="submit" value="Cadastrar"/>
        </form>
      </div>
      <div class="form login">
        <header @click="toggleForms">Login</header>
        <form @submit.prevent="loginUser">
          <input type="email" placeholder="E-mail" required v-model="loginData.emailUsuario"/>
          <input type="password" placeholder="Senha" required v-model="loginData.senhaUsuario" class="password-input" ref="loginPassword">
          <span class="password-login" @click="togglePasswordVisibility('login')" ref="loginPasswordToggle"> 
            <img src="/red-eyes.png" alt="Mostrar Senha">
          </span>
          <input type="submit" value="Entrar"/>
        </form>
      </div>
    </section>
  </div>
</template>

<script>
import AuthService from '@/services/AuthService';

export default {
  name: 'LogInSignUp',
  data() {
    return {
      isLogin: false,
      notificationMessage: '',
      alertClass: '',
      signupData: {
        nomeUsuario: '',
        apartamentoUsuario: '',
        emailUsuario: '',
        senhaUsuario: ''
      },
      loginData: {
        emailUsuario: '',
        senhaUsuario: ''
      },
      acceptTerms: false
    };
  },
  methods: {
    togglePasswordVisibility(formType) {
      const passwordInput = formType === 'signup' ? this.$refs.signupPassword : this.$refs.loginPassword;
      const passwordToggle = formType === 'signup' ? this.$refs.signupPasswordToggle : this.$refs.loginPasswordToggle;

      if (passwordInput && passwordToggle) {
        if (passwordInput.type === 'password') {
          passwordInput.type = 'text';
          passwordToggle.classList.add('active');
        } else {
          passwordInput.type = 'password';
          passwordToggle.classList.remove('active');
        }
      } else {
        console.error('Referência não encontrada para passwordInput ou passwordToggle.');
      }
    },
    setNotification(message, type) {
      this.notificationMessage = message;
      this.alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
      setTimeout(() => {
        this.notificationMessage = '';
        this.alertClass = '';
      }, 5000); // Limpar a mensagem após 5 segundos
    },
    async loginUser() {
      try {
        const response = await fetch(`http://localhost:8080/validacadastro/${this.loginData.emailUsuario}/${this.loginData.senhaUsuario}`);
        
        if (!response.ok) {
          this.setNotification('O e-mail e senha informados estão incorretos', 'danger');
          return;
        }

        const userId = await response.json();
        console.log('Resposta da API (ID do usuário):', userId);

        if (typeof userId === 'number') {
          AuthService.login('fake-token');  // Substitua 'fake-token' pelo token real se houver
          console.log('ID do usuário:', userId);
          localStorage.setItem('userId', userId);
          this.setNotification('Login bem-sucedido', 'success');

          // Atraso antes de redirecionar para a página Home.vue
          setTimeout(() => {
            this.$router.push({ name: 'Home' });
          }, 1000); // 2 segundos

        } else {
          this.setNotification('Erro ao obter o ID do usuário', 'danger');
          console.error('ID do usuário não é um número:', userId);
        }
      } catch (error) {
        this.setNotification('Erro ao tentar se logar', 'danger');
        console.error('Erro ao tentar fazer login:', error);
      }
    },
    async signUpUser() {
      try {
        const response = await fetch('http://localhost:8080/newusuario', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.signupData)
        });

        if (response.ok) {
          this.setNotification('Cadastro bem-sucedido', 'success');
          this.clearSignUpForm();
          this.isLogin = true;
        } else {
          this.setNotification('Falha no cadastro', 'danger');
        }
      } catch (error) {
        this.setNotification('Erro ao tentar cadastrar', 'danger');
        console.error('Erro ao tentar cadastrar:', error);
      }
    },
    clearSignUpForm() {
      this.signupData.nomeUsuario = '';
      this.signupData.apartamentoUsuario = '';
      this.signupData.emailUsuario = '';
      this.signupData.senhaUsuario = '';
      this.acceptTerms = false;
    },
    toggleForms() {
      this.isLogin = !this.isLogin;
    }
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
}

.password-input {
  position: relative;
  padding-right: 40px;
}

.password-signup {
  position: absolute;
  top: 49.8%; 
  right: 40px; 
  transform: translateY(-0%);
  cursor: pointer;
}

.password-signup img {
  width: 25px;
  height: auto;
}

.password-signup.active img {
  filter: invert(20%) sepia(100%) saturate(300%) hue-rotate(10deg);
}

.login-signup-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0faff;
}

.password-login {
  position: absolute;
  top: 29.5%; 
  right: 150px; 
  transform: translateY(-0%);
  cursor: pointer;
}

.password-login img {
  width: 25px;
  height: auto;
}

.password-login.active img {
  filter: invert(20%) sepia(100%) saturate(300%) hue-rotate(10deg);
}

.login-login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0faff;
}

.wrapper {
  position: relative;
  max-width: 470px;
  width: 100%;
  border-radius: 12px;
  padding: 20px 30px 120px;
  background: #4070f4;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.form.login {
  position: absolute;
  left: 50%;
  bottom: -86%;
  transform: translateX(-50%);
  width: calc(100% + 220px);
  padding: 20px 140px;
  border-radius: 50%;
  height: 100%;
  background: #fff;
  transition: all 0.6s ease;
}

.wrapper.active .form.login {
  bottom: -15%;
  border-radius: 35%;
  box-shadow: 0 -5px 10px rgba(0, 0, 0, 0.1);
}

.form header {
  font-size: 30px;
  text-align: center;
  color: #fff;
  font-weight: 600;
  cursor: pointer;
}

.form.login header {
  color: #333;
  opacity: 0.6;
}

.wrapper.active .form.login header {
  opacity: 1;
}

.wrapper.active .signup header {
  opacity: 0.6;
}

.wrapper form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 40px;
}

form input {
  height: 40px;
  outline: none;
  border: none;
  padding: 0 15px;
  font-size: 16px;
  font-weight: 400;
  color: #333;
  border-radius: 8px;
  background: #fff;
}

.form.login input {
  border: 1px solid #aaa;
}

.form.login input:focus {
  box-shadow: 0 1px 0 #ddd;
}

form .checkbox {
  display: flex;
  align-items: center;
  gap: 10px;
}

.checkbox input[type="checkbox"] {
  height: 16px;
  width: 16px;
  accent-color: #fff;
  cursor: pointer;
}

form .checkbox label {
  cursor: pointer;
  color: #fff;
}

form a {
  color: #333;
  text-decoration: none;
}

form a:hover {
  text-decoration: underline;
}

form input[type="submit"] {
  margin-top: 15px;
  padding: none;
  font-size: 18px;
  font-weight: 500;
  cursor: pointer;
}

.form.login input[type="submit"] {
  background: #4070f4;
  color: #fff;
  border: none;
}

.alert {
  position: fixed;
  top: 10px;
  left: 50%;
  transform: translateX(-50%);
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 16px;
  z-index: 1000;
  display: inline-block;
}

.alert-success {
  background-color: #4CAF50;
  color: white;
}

.alert-danger {
  background-color: #f44336;
  color: white;
}
</style>
