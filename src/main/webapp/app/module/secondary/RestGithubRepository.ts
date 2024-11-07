import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import { GithubRepository } from '../domain/GithubRepository';
import type { GithubAuthorizationCode } from '@/module/domain/GithubAuthorizationCode';
import type { GithubAuthorizationUrl } from '@/module/domain/GithubAuthorizationUrl';
import type { GithubToken } from '@/module/domain/GithubToken';
import { mapToGithubToken, RestGithubToken } from './RestGithubToken';

export class RestGithubRepository implements GithubRepository {
  constructor(private readonly axiosInstance: AxiosHttp) {}

  getAuthorizationUrl(): Promise<GithubAuthorizationUrl> {
    return this.axiosInstance.get<GithubAuthorizationUrl>('/api/github/oauth2/authorization')
      .then(response => response.data);
  }

  authenticate(code: GithubAuthorizationCode): Promise<GithubToken> {
    return this.axiosInstance
      .get<RestGithubToken>(`/api/github/oauth2/callback?code=${encodeURIComponent(code)}`)
      .then(mapToGithubToken);
  }
}
