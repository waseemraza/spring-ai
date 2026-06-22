# Getting Started

### Install ollama locally
```curl -fsSL https://ollama.com/install.sh | sh```

### Execution of ollama running as system service on linux
sudo systemctl status ollama
sudo systemctl start ollama
sudo systemctl stop ollama

### Useful commands
```
ollama run <model with version>

// list all models
ollama ls

//  list only running models
ollama ps
```