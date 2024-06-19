<template>
  <div>
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
              <button class="close-button" @click="removeNotification(panel.title, i)">X</button>
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
    console.error('Erro ao obter dados:', error);
    errorMessage.value = 'Erro ao carregar dados. Por favor, tente novamente mais tarde.';
  }
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
    items: reunioes
  },
  {
    icon: 'bx bx-bell icon',
    title: 'Ocorrências',
    items: ocorrencias
  },
  {
    icon: 'bx bx-like icon',
    title: 'Achados e perdidos',
    items: achadosPerdidos
  }
]);

// Atualiza os painéis dinamicamente quando os dados mudam
watch([reunioes, ocorrencias, achadosPerdidos], () => {
  panels.value = [
    {
      icon: 'bx bx-user-voice icon',
      title: 'Reuniões',
      items: reunioes.value
    },
    {
      icon: 'bx bx-bell icon',
      title: 'Ocorrências',
      items: ocorrencias.value
    },
    {
      icon: 'bx bx-like icon',
      title: 'Achados e perdidos',
      items: achadosPerdidos.value
    }
  ];
});

// Remover notificação
const removeNotification = (panelTitle, index) => {
  try {
    if (panelTitle === 'Reuniões') {
      reunioes.value.splice(index, 1);
    } else if (panelTitle === 'Ocorrências') {
      ocorrencias.value.splice(index, 1);
    } else if (panelTitle === 'Achados e perdidos') {
      achadosPerdidos.value.splice(index, 1);
    }

    // Atualiza os painéis apenas após a remoção local
    panels.value = [
      {
        icon: 'bx bx-user-voice icon',
        title: 'Reuniões',
        items: [...reunioes.value]  // Cria uma cópia para garantir a reatividade
      },
      {
        icon: 'bx bx-bell icon',
        title: 'Ocorrências',
        items: [...ocorrencias.value]  // Cria uma cópia para garantir a reatividade
      },
      {
        icon: 'bx bx-like icon',
        title: 'Achados e perdidos',
        items: [...achadosPerdidos.value]  // Cria uma cópia para garantir a reatividade
      }
    ];
  } catch (error) {
    console.error('Erro ao remover notificação:', error);
    errorMessage.value = 'Erro ao remover notificação. Por favor, tente novamente mais tarde.';
  }
};


// Minimizar/maximizar notificação
const minimizeNotification = (panelTitle, index) => {
  const item = panels.value.find(panel => panel.title === panelTitle)?.items[index];
  if (item) {
    item.minimized = !item.minimized;
  }
};

// Formatação de data e hora para reuniões
const formatDate = (dateStr, hour) => {
  const date = new Date(dateStr);
  const day = date.getDate();
  const month = date.getMonth() + 1;
  const year = date.getFullYear();
  const hours = date.getHours();
  const minutes = (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
  let formattedDate = `${day}/${month}/${year}`;

  if (hour && hour.includes(':')) {
    const [hourPart, minutePart] = hour.split(':');
    const hourNumber = parseInt(hourPart, 10);

    // Determinar o sufixo de AM ou PM
    const suffix = hourNumber >= 12 ? 'PM' : 'AM';

    // Converter a hora para o formato de 12 horas
    const formattedHour = hourNumber % 12 || 12;

    formattedDate += ` às ${formattedHour}:${minutePart} ${suffix}`;
  }

  return formattedDate;
};
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
