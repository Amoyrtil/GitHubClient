# GitHubClient

## 概要
GitHubのユーザーを検索し、該当ユーザーの詳細情報を表示することができます。

## ビルド前の準備
プロジェクト直下の`local.properties`に対して、以下のように環境変数を追記してください。  
このトークンを追記することで、APIのレート制限が緩和されます。  
トークンに関する詳細は[こちら](https://docs.github.com/ja/rest/authentication/authenticating-to-the-rest-api?apiVersion=2022-11-28)。

```
PERSONAL_ACCESS_TOKEN=XXXSampleToken
```
