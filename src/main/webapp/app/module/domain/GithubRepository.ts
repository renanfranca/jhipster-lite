import type { GithubAuthorizationUrl } from '@/module/domain/GithubAuthorizationUrl';
import type { GithubToken } from '@/module/domain/GithubToken';
import type { GithubOrganization } from '@/module/domain/GithubOrganization';

export interface GithubRepository {
  getAuthorizationUrl(): Promise<GithubAuthorizationUrl>;
  authenticate(code: string): Promise<GithubToken>;
  listOrganizations(token: GithubToken): Promise<GithubOrganization[]>;
}
