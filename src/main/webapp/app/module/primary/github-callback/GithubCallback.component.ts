import { inject } from '@/injections';
import { GITHUB_REPOSITORY } from '@/module/application/ModuleProvider';
import type { GithubToken } from '@/module/domain/GithubToken';
import { defineComponent, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

export default defineComponent({
  name: 'GithubCallbackVue',
  setup() {
    const githubRepository = inject(GITHUB_REPOSITORY);
    const route = useRoute();
    const router = useRouter();
    const loading = ref(true);
    const error = ref<string | null>(null);
    const countdown = ref(5);

    const saveTokenToLocalStorage = (token: GithubToken) => {
      localStorage.setItem('github-token', JSON.stringify(token));
    };

    const startCountdown = () => {
      const timer = setInterval(() => {
        countdown.value--;
        if (countdown.value === 0) {
          clearInterval(timer);
          router.push('/');
        }
      }, 1000);
    };

    onMounted(() => {
      const code = route.query.code as string;
      if (!code) {
        error.value = 'No authorization code provided';
        loading.value = false;
        return;
      }

      githubRepository
        .authenticate(code)
        .then(token => {
          saveTokenToLocalStorage(token);
          loading.value = false;
          startCountdown();
        })
        .catch(err => {
          error.value = 'Failed to authenticate with GitHub';
          loading.value = false;
          console.error('Authentication error:', err);
        });
    });

    return {
      loading,
      error,
      countdown,
    };
  },
});
