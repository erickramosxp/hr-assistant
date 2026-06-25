# HR Assistant

O HR Assistant é um projeto de estudos desenvolvido com Spring Boot e Spring AI para explorar conceitos modernos de Inteligência Artificial aplicados a assistentes corporativos.

O projeto utiliza a arquitetura RAG (Retrieval-Augmented Generation), permitindo que a IA consulte documentos previamente indexados para gerar respostas mais contextualizadas e relevantes. Além disso, conta com memória conversacional para manter o contexto das interações entre o usuário e o assistente.

## Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring AI
* Google Gemini
* Ollama
* PostgreSQL
* PGVector
* Redis
* Docker

## Objetivos do Projeto

* Estudar integração de LLMs com aplicações Java.
* Implementar busca semântica utilizando embeddings vetoriais.
* Explorar arquiteturas RAG.
* Utilizar memória conversacional com Redis.
* Aprender boas práticas para desenvolvimento de aplicações baseadas em IA.

## Estrutura do Projeto

```bash
src/main/
├── java/br/com/devsuperior/hr_assistant/
│   ├── HrAssistantApplication.java
│   ├── config/ChatClientConfig.java        # ChatClient, memoria e advisors
│   ├── ingestion/IngestionController.java   # POST /ingest
│   ├── ingestion/IngestionService.java      # le o PDF, divide e indexa
│   ├── chat/ChatController.java             # POST /chat/stream (SSE)
│   ├── chat/ChatService.java                # fala com o ChatClient
│   ├── chat/PromptLoggingAdvisor.java       # loga o prompt final e a resposta
│   └── chat/dto/ChatRequest.java
└── resources/
    ├── application.yml                       # infra comum (Postgres, Redis, RAG)
    ├── application-anthropic.yml             # profile do provedor (Claude + Ollama)
    ├── docs/aurora_car_dealer_politicas_rh.pdf
    ├── prompts/context-prompt.st             # o template de grounding
    └── static/index.html                     # a pagina de chat
```

## Funcionalidades Planejadas

* Indexação de documentos.
* Busca semântica por similaridade.
* Recuperação de contexto com PGVector.
* Conversas contextualizadas com memória.
* Suporte a diferentes provedores de IA através de perfis do Spring.
