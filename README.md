# 🟡 Pac-Man em Java (Swing)

Projeto de um jogo inspirado no clássico **Pac-Man**, desenvolvido em **Java** utilizando **Swing** para a interface gráfica.

O principal objetivo deste projeto foi praticar conceitos de **Programação Orientada a Objetos (POO)**, organização de código, lógica de jogos e implementação de comportamentos inteligentes para os fantasmas, mantendo uma estrutura limpa e modular.

---

## 🎮 Funcionalidades

- ✅ Menu principal
- ✅ Sistema de pontuação
- ✅ Contador de vidas do Pac-Man
- ✅ Power Pellet (modo de vulnerabilidade dos fantasmas)
- ✅ IA dos fantasmas com movimentação inteligente
- ✅ Sistema de estados dos fantasmas
- ✅ Tela de vitória
- ✅ Tela de Game Over
- ✅ Reinício das posições após a perda de uma vida
- ✅ Estrutura organizada em pacotes

---

## 👻 Comportamento dos Fantasmas

Os fantasmas utilizam uma máquina de estados implementada com um `enum`, tornando o comportamento mais organizado e fácil de manter.

### Estados

- 🎯 **CHASE** — Persegue o Pac-Man.
- 🔵 **FRIGHTENED** — Fica vulnerável após o Pac-Man consumir um Power Pellet.
- 👀 **EATEN** — Após ser capturado, retorna ao ponto inicial apenas com os olhos.
- 🔒 **LOCKED** — Permanece preso na base por alguns segundos antes de voltar ao jogo.

### Durante o Power Pellet

Quando o Pac-Man consome uma Power Pellet:

- 🔹 Os fantasmas ficam azuis;
- 🔹 Passam a fugir do jogador;
- 🔹 Sua velocidade é reduzida.

### Quando um fantasma é capturado

Após ser capturado pelo Pac-Man:

- 👀 Apenas os olhos permanecem visíveis;
- 🏠 O fantasma retorna à base;
- ⏳ Permanece bloqueado por alguns segundos;
- 🎯 Depois retorna ao estado **CHASE**.

---

## 🎮 Controles

| Tecla | Ação |
|-------|------|
| ⬆️ | Mover para cima |
| ⬇️ | Mover para baixo |
| ⬅️ | Mover para a esquerda |
| ➡️ | Mover para a direita |
| **ENTER** | Confirmar opções do menu |

---

## 💻 Requisitos

- **Java 21** ou superior
- Qualquer IDE compatível com Java, como:
  - IntelliJ IDEA
  - Eclipse
  - Visual Studio Code
  - NetBeans

---

## 🚀 Como executar

### 1. Clone o repositório

```bash
git clone https://github.com/marcogasparotto/PacMan.git
```

### 2. Abra o projeto em sua IDE

Importe o projeto normalmente utilizando sua IDE de preferência.

### 3. Execute a classe principal

```text
br.com.marco.pacman.Main
```

Após a execução, o jogo será iniciado normalmente.

---

## 🛠️ Tecnologias utilizadas

- ☕ Java 21
- 🖥️ Java Swing
- 🎮 Programação Orientada a Objetos (POO)

---

## 📚 Objetivos do projeto

Este projeto foi desenvolvido com o propósito de praticar e consolidar conhecimentos em:

- Programação Orientada a Objetos;
- Organização de projetos Java;
- Desenvolvimento de jogos 2D;
- Estruturas de dados;
- Máquinas de estados (State Machine);
- Lógica de movimentação e comportamento de NPCs.

---

## 📌 Observação

Este projeto é uma recriação inspirada no clássico **Pac-Man**, desenvolvida exclusivamente para fins educacionais e de aprendizado.

Não possui fins comerciais e não tem qualquer vínculo com os detentores da franquia original.
