<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

// Dados reativos
let reservas = ref([]);
let ocorrencias = ref([]);
let reunioes = ref([]);
let achadosPerdidos = ref([]);

// Função para buscar dados
const fetchData = () => {
  axios.get('http://localhost:8080/reserva')
    .then(response => {
      reservas.value = response.data;
    })
    .catch(error => {
      console.error('Erro ao obter reservas:', error);
    });

  axios.get('http://localhost:8080/comunicado')
    .then(response => {
      ocorrencias.value = response.data;
    })
    .catch(error => {
      console.error('Erro ao obter ocorrências:', error);
    });

  axios.get('http://localhost:8080/assembleia')
    .then(response => {
      reunioes.value = response.data;
    })
    .catch(error => {
      console.error('Erro ao obter reuniões:', error);
    });

  axios.get('http://localhost:8080/achado')
    .then(response => {
      achadosPerdidos.value = response.data;
    })
    .catch(error => {
      console.error('Erro ao obter achados e perdidos:', error);
    });
};

// Chamar fetchData quando o componente é montado
onMounted(fetchData);

// Roteamento
const router = useRouter();
const goToReserve = () => router.push({ name: 'CalendarReserve' });
const goToOccurrence = () => router.push({ name: 'Occurrence' });
const goToMeeting = () => router.push({ name: 'Meeting' });

// Atualizar os dados quando a rota mudar
const route = useRoute();
watch(route, () => {
  fetchData();
});

// Painéis
const panels = ref([
  {
    icon: 'bx bx-user-voice icon',
    title: 'Reuniões',
    items: [] //reunioes 
  },
  {
    icon: 'bx bx-bell icon',
    title: 'Ocorrências',
    items: [] //ocorrencias 
  },
  {
    icon: 'bx bx-like icon',
    title: 'Achados e perdidos',
    items: [] //achadosPerdidos 
  }
]);
</script>

<template>
  <div>
    <div class="contact-info">
      <h3>Bem-vindo(a)</h3>
      <h4>Em caso de dúvidas ou melhorias, entre em contato conosco:</h4>
      <p>CONDOCONNECT ADMINISTRADORA DE CONDOMÍNIOS - (47) 3345-6258</p>
      <p>(47) 99243-0217 - <a href="mailto:atendimentocondoconnect@gmail.com">atendimentocondoconnect@gmail.com</a></p>
    </div>

    <div class="button-container">
      <div class="button" @click="goToReserve">
        <img src="./../components/icons/imgReserve.png" alt="ImgReserve">
        <p>Reserva</p>
      </div>
      <div class="button" @click="goToOccurrence">
        <img src="./../components/icons/imgOccurrence.png" alt="ImgOccurrence">
        <p>Ocorrências</p>
      </div>
      <div class="button" @click="goToMeeting">
        <img src="./../components/icons/imgMeeting.png" alt="ImgMeeting">
        <p>Reuniões</p>
      </div>
    </div>

    <div class="panel" v-for="(panel, index) in panels" :key="index">
      <i :class="panel.icon"></i>
      <span class="text nav-text">{{ panel.title }}</span>

      <ul v-if="panel.items.length > 0" class="list">
        <li class="list-item" v-for="(item, i) in panel.items" :key="i">{{ item.nome }}</li>
      </ul>

      <p v-else>Não há {{ panel.title.toLowerCase() }} no momento</p>
    </div>
  </div>
</template>

<style>
.template {
  margin: 0;
  padding: 20px;
  display: flex;
  flex-direction: column;
  background-color: #E4E9F7;
}

.contact-info {
  text-align: left;
  margin-left: 150px;
}

.contact-info a {
  text-decoration: none;
  color: inherit;
  transition: color 0.3s ease;
}

.contact-info a:hover {
  color: rgb(0, 0, 255);
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
  cursor: pointer;
  transition: border-color 0.3s ease;
  width: 150px;
  margin-right: 10px;
  margin-top: 20px;
}

.button:last-child {
  margin-right: 0;
}

.button:hover {
  border-color: #999;
  background-color: #bbceeb;
}

.button img {
  width: 50px;
  height: 50px;
  margin-bottom: 5px;
}

.button p {
  margin: 0;
  color: #555;
  font-weight: bold;
  font-size: 14px;
  text-align: center;
}

.panel {
  width: 1100px;
  display: flex;
  flex-direction: column;
  padding: 10px;
  background-color: #fff;
  margin-top: 20px;
  margin-left: 150px;
}

.panel i {
  margin-right: 10px;
}

.panel .text {
  font-weight: bold;
  margin-right: 10px;
}

.panel .list {
  margin-top: 5px;
  padding-left: 20px;
}

.panel .list-item {
  list-style: circle;
}

.panel p {
  margin: 10px 0 0;
  border-top: 1px solid #ccc;
  padding-top: 10px;
}
</style>
