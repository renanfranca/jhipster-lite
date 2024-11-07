import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import { GithubRepository, GithubAuthorizationUrl, GithubToken } from '../domain/GithubRepository';

export class RestGithubRepository implements GithubRepository {
  constructor(private readonly axiosInstance: AxiosHttp) {}

  getAuthorizationUrl(): Promise<GithubAuthorizationUrl> {
    return this.axiosInstance.get<GithubAuthorizationUrl>('/api/github/oauth2/authorization')
      .then(response => response.data);
  }

  authenticate(code: string): Promise<GithubToken> {
    return this.axiosInstance.get<GithubToken>(`/api/github/oauth2/callback?code=${encodeURIComponent(code)}`)
      .then(response => response.data);
  }
}
