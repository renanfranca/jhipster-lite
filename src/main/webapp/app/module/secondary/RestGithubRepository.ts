import type { GithubAuthorizationCode } from '@/module/domain/GithubAuthorizationCode';
import type { GithubAuthorizationUrl } from '@/module/domain/GithubAuthorizationUrl';
import { GithubOrganization } from '@/module/domain/GithubOrganization';
import type { GithubToken } from '@/module/domain/GithubToken';
import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import { GithubRepository } from '../domain/GithubRepository';
import { mapToGithubToken, RestGithubToken } from './RestGithubToken';

export class RestGithubRepository implements GithubRepository {
  constructor(private readonly axiosInstance: AxiosHttp) {}

  getAuthorizationUrl(): Promise<GithubAuthorizationUrl> {
    return this.axiosInstance.get<GithubAuthorizationUrl>('/api/github/oauth2/authorization').then(response => response.data);
  }

  authenticate(code: GithubAuthorizationCode): Promise<GithubToken> {
    return this.axiosInstance.get<RestGithubToken>(`/api/github/oauth2/callback?code=${encodeURIComponent(code)}`).then(mapToGithubToken);
  }

  listOrganizations(token: GithubToken): Promise<GithubOrganization[]> {
    return this.axiosInstance
      .get<GithubOrganization[]>('/api/github/organizations', {
        headers: {
          Authorization: `${token.tokenType} ${token.accessToken}`,
        },
      })
      .then(response => response.data);
  }

  authenticated(): boolean {
    const storedToken = localStorage.getItem('github-token');
    if (!storedToken) {
      return false;
    }

    try {
      const token = JSON.parse(storedToken) as GithubToken;
      return Boolean(token.accessToken);
    } catch {
      return false;
    }
  }
}
