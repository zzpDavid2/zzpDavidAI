import os
import openai

fileName = "ChatBotTemp.txt"

thisPath = os.path.abspath(os.path.realpath(__file__))

filePath = thisPath.replace("ChatBot.py", fileName)


# filePath = os.path.dirname(fileName)

print(filePath)

file = open(filePath, "a+")

openai.api_key = "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"

start_sequence = "\nAI:"
restart_sequence = "\nHuman: "

file.seek(0)

myPrompt = file.read() + "\nAI:"

response = openai.Completion.create(
  engine="davinci",
  prompt= myPrompt,
  # prompt="The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly.\n\nHuman: Hello, who are you?\nAI: I am an AI created by OpenAI. How can I help you today?\nHuman: ",
  temperature=0.9,
  max_tokens=150,
  top_p=1,
  frequency_penalty=0,
  presence_penalty=0.6,
  stop=["\n", " Human:", " AI:"]
)

result = response.choices[0].text

result = result.replace(u'\xa0', u' ')

print(result)

file.write("AI:" + result)
file.write("\n" + "Human: ")

file.close()