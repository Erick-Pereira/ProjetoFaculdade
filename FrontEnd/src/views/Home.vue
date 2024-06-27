<template>
  <div class="template">
    <div class="contact-info">
      <h3>Bem-vindo(a)</h3>
      <h4>Em caso de dúvidas ou melhorias, entre em contato conosco:</h4>
      <p>CONDOCONNECT ADMINISTRADORA DE CONDOMÍNIOS - (47) 3345-6258</p>
      <p>(47) 99243-0217 - <a href="mailto:atendimentocondoconnect@gmail.com">atendimentocondoconnect@gmail.com</a></p>
    </div>

    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

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

    <div class="notification-container">
      <div v-for="(panel, index) in panels" :key="index" class="panel">
        <div class="notification">
          <div class="notification-header">
            <i :class="panel.icon"></i>
            <span class="notification-title">{{ panel.title }}</span>
          </div>
          <ul v-if="panel.items.length > 0" class="notification-list">
            <li class="notification-item" v-for="(item, i) in panel.items" :key="i">
              <button class="minimize-button" @click="minimizeNotification(panel.title, i)">
                {{ item.minimized ? '+' : '-' }}
              </button>
              <div class="notification-item-details" v-if="!item.minimized">
                <span class="notification-item-title">{{ item.titulo || item.tituloComunicado || item.tituloAchado }}</span>
                <p class="notification-item-description">{{ item.descricao || item.descricaoComunicado || item.descricaoAchado }}</p>
                <span class="notification-item-date">{{ formatDate(item.data, panel.title === 'Reuniões' ? item.hora : undefined) }}</span>
              </div>
              <div class="notification-item-details" v-else>
                <span class="notification-item-title">{{ item.titulo || item.tituloComunicado || item.tituloAchado }}</span>
              </div>
            </li>
          </ul>
          <p v-else class="notification-empty">Não há {{ panel.title.toLowerCase() }} no momento</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

// Dados reativos
let ocorrencias = ref([]);
let reunioes = ref([]);
let achadosPerdidos = ref([]);
let errorMessage = ref('');

// Função para buscar dados
const fetchData = async () => {
  try {
    const ocorrenciasResponse = await axios.get('http://localhost:8080/comunicadolist');
    console.log('Ocorrências:', ocorrenciasResponse.data); // Log de depuração
    ocorrencias.value = ocorrenciasResponse.data;

    const reunioesResponse = await axios.get('http://localhost:8080/assembleialist');
    console.log('Reuniões:', reunioesResponse.data); // Log de depuração
    reunioes.value = reunioesResponse.data;

    const achadosPerdidosResponse = await axios.get('http://localhost:8080/achadolist');
    console.log('Achados e Perdidos:', achadosPerdidosResponse.data); // Log de depuração
    achadosPerdidos.value = achadosPerdidosResponse.data;
  } catch (error) {
    console.error('Erro ao buscar dados:', error);
  }
};

// Lógica para minimizar notificações
const minimizeNotification = (title, index) => {
  switch (title) {
    case 'Ocorrências':
      ocorrencias.value[index].minimized = !ocorrencias.value[index].minimized;
      break;
    case 'Reuniões':
      reunioes.value[index].minimized = !reunioes.value[index].minimized;
      break;
    case 'Achados e Perdidos':
      achadosPerdidos.value[index].minimized = !achadosPerdidos.value[index].minimized;
      break;
    default:
      break;
  }
};

// Formatando data
const formatDate = (date, time) => {
  const formattedDate = new Date(date);
  let formattedString = `${formattedDate.toLocaleDateString()}`;
  if (time) {
    formattedString += ` ${time}`;
  }
  return formattedString;
};

// Navegação
const router = useRouter();
const goToReserve = () => router.push({ name: 'Reserve' });
const goToOccurrence = () => router.push({ name: 'Occurrence' });
const goToMeeting = () => router.push({ name: 'Meeting' });

// Dados dinâmicos para o template
const panels = ref([
  { title: 'Ocorrências', items: ocorrencias.value, icon: 'fas fa-exclamation-circle' },
  { title: 'Reuniões', items: reunioes.value, icon: 'fas fa-users' },
  { title: 'Achados e Perdidos', items: achadosPerdidos.value, icon: 'fas fa-search' }
]);

// Atualizando dados ao montar o componente
onMounted(() => {
  fetchData();
});

// Observando mudanças nos dados
watch(ocorrencias, () => {
  panels.value[0].items = ocorrencias.value;
});
watch(reunioes, () => {
  panels.value[1].items = reunioes.value;
});
watch(achadosPerdidos, () => {
  panels.value[2].items = achadosPerdidos.value;
});
</script>

<style scoped>
.notification-container {
  display: flex;
  flex-direction: column;
}

.panel {
  margin-bottom: 20px;
}

.notification {
  padding: 10px;
  background-color: #f0f0f0;
  border-radius: 5px;
}

.notification-header {
  display: flex;
  align-items: center;
}

.notification-header i {
  margin-right: 10px;
}

.notification-title {
  font-size: 18px;
  font-weight: bold;
}

.notification-list {
  list-style-type: none;
  padding: 0;
}

.notification-item {
  margin-top: 10px;
  position: relative;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
}

.notification-item-title {
  font-weight: bold;
}

.notification-item-details {
  color: #666;
}

.notification-empty {
  margin-top: 10px;
  color: #999;
}

.notification-item-description {
  color: #666;
}

.notification-item-date {
  color: #999;
  font-size: 14px;
  margin-top: 5px;
}

.close-button {
  position: absolute;
  top: 5px;
  right: 5px;
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #999;
  transition: color 0.3s ease;
}

.close-button:hover {
  color: #666;
}

.minimize-button {
  position: absolute;
  top: 5px;
  right: 25px; 
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #999;
  transition: color 0.3s ease;
}

.minimize-button:hover {
  color: #666;
}

.minimized {
  display: none; 
}

.template {
  margin: 0;
  padding: 20px;
  display: flex;
  flex-direction: column;
  background-color: #e4e9f7;
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
