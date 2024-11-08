import { GITHUB_REPOSITORY } from '@/module/application/ModuleProvider';
import { defineComponent, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { inject } from '@/injections';
import { IconVue } from '@/shared/icon/infrastructure/primary';
import type { GithubToken } from '@/module/domain/GithubToken';

export default defineComponent({
  name: 'GithubConfigurationVue',
  components: { IconVue },
  setup() {
    const githubRepository = inject(GITHUB_REPOSITORY);
    const route = useRoute();
    const githubToken = ref<GithubToken | null>(null);

    onMounted(() => {
      const code = route.query.code as string;
      if (code) {
        githubRepository
          .authenticate(code)
          .then(token => {
            githubToken.value = token;
          })
          .catch(error => console.error('Authentication error:', error));
      }
    });

    const connectToGithub = () => {
      githubRepository
        .getAuthorizationUrl()
        .then(url => {
          window.location.href = url;
        })
        .catch(error => console.error('Error getting authorization URL:', error));
    };

    return {
      connectToGithub,
      githubToken,
    };
  },
});
