<template>
  <div class="login-signup-container">
    <section class="wrapper">
      <div class="form signup">
        <header>Cadastrar-se</header>
        <form @submit.prevent="signUpUser">
          <input type="text" placeholder="Nome completo" required name="signupName" ref="signupName" autocomplete="name"/>
          <input type="text" placeholder="Complemento" required name="signupFlat" ref="signupFlat" autocomplete="address-level2"/>
          <input type="email" placeholder="E-mail" required name="signupemailUsuario" ref="signupemailUsuario" autocomplete="emailUsuario"/>
          <input type="password" placeholder="Senha" required name="signupsenhaUsuario" ref="signupsenhaUsuario" autocomplete="new-senhaUsuario"/>
          <div class="checkbox">
            <input type="checkbox" id="signupCheck"/>
            <label for="signupCheck">Eu aceito os <strong>Termos de uso</strong> e <strong>Política de privacidade</strong></label>
          </div>
          <input type="submit" value="Cadastrar"/>
        </form>
      </div>
      <div class="form login">
        <header>Login</header>
        <form @submit.prevent="loginUser">
          <input type="email" placeholder="E-mail" required name="emailUsuario" ref="emailUsuario" autocomplete="emailUsuario"/>
          <input type="password" placeholder="Senha" required name="senhaUsuario" ref="senhaUsuario" autocomplete="current-senhaUsuario"/>
          <input type="submit" value="Entrar"/>
        </form>
      </div>
    </section>
  </div>
</template>
 
<script>
export default {
  name: 'LogInSignUp',
  mounted() {
    const wrapper = document.querySelector(".wrapper");
    const signupHeader = document.querySelector(".signup header");
    const loginHeader = document.querySelector(".login header");
 
    loginHeader.addEventListener("click", () => {
      wrapper.classList.add("active");
    });
    signupHeader.addEventListener("click", () => {
      wrapper.classList.remove("active");
    });
  },
  methods: {
    async loginUser() {
      try {
        const emailUsuario = this.$refs.emailUsuario.value;
        const senhaUsuario = this.$refs.senhaUsuario.value;
        const response = await fetch(`http://localhost:8080/validacadastro/${emailUsuario}/${senhaUsuario}`);
        const data = await response.json();
        if (response.ok) {
          console.log('Login bem-sucedido, ID do usuário:', data);
          this.$router.push({ name: 'Home' });
        } else {
          console.error('Login falhou:', data);
        }
      } catch (error) {
        console.error('Erro ao tentar fazer login:', error);
      }
    },
    async signUpUser() {
      try {
        const nomeUsuario = this.$refs.signupName.value;
        const apartamentoUsuario = this.$refs.signupFlat.value;
        const emailUsuario = this.$refs.signupemailUsuario.value;
        const senhaUsuario = this.$refs.signupsenhaUsuario.value;
        const response = await fetch('http://localhost:8080/newusuario', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ nomeUsuario, apartamentoUsuario, emailUsuario, senhaUsuario })
        });
        if (response.ok) {
          console.log('Cadastro bem-sucedido');
          this.$router.push({ name: 'Home' });
        } else {
          console.error('Falha no cadastro:', response.statusText);
        }
      } catch (error) {
        console.error('Erro ao tentar cadastrar:', error);
      }
    }
  }
}
</script>
 
<style>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap");
 
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
}
 
.login-signup-container {
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
</style>