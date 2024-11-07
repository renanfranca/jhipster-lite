type GithubTokenAccessToken = string;
type GithubTokenTokenType = string;

export interface GithubToken {
  accessToken: GithubTokenAccessToken;
  tokenType: GithubTokenTokenType;
}
