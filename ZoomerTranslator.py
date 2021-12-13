from sys import argv
import random
import requests
import json
import re

class ZoomerTranslator:
    def __init__(self):
        with open('./api_keys.json', 'r') as json_file:
            api_keys = json.load(json_file)
        self.api_key = api_keys['yandex_translate']
        
        with open(file='zoomer_dict.json', mode='r') as json_file:
            self.zoomer_dict = json.load(json_file)
            
        with open(file='training_zoomer_dict.json', mode='r') as json_file:
            self.training_zoomer_dict = json.load(json_file)

    def word_to_emoji(self, word, lang):
        IAM_TOKEN = self.api_key
        folder_id = 'b1gdiuaovu3v0cvdf7so'
        target_language = lang
        texts = [word]

        body = {
            "targetLanguageCode": target_language,
            "texts": texts,
            "folderId": folder_id,
        }

        headers = {
            "Content-Type": "application/json",
            "Authorization": "Bearer {0}".format(IAM_TOKEN)
        }
	    
        response = requests.post('https://translate.api.cloud.yandex.net/translate/v2/translate',
            json = body,
            headers = headers
        )
    
        try:
            answer = response.text
        except Exception:
            answer = word
        finally:
            return answer

    def boomer_to_zoomer(self, text_split, lang=None):
        with open(file='zoomer_dict.json', mode="r") as json_file:
            self.zoomer_dict = json.load(json_file)

        new_text = []

        for word in text_split:
            spec = re.findall(r'[,.;\-!?]', word)
            word = re.sub('[,.;\-?!](?!\s+\d$)', '', word)
            new_word = word
            
            if word in self.zoomer_dict.keys():
                new_word = self.zoomer_dict[word]
            elif lang is not None and random.randint(0, 1):
                new_word = translate(self.api_key, word, lang)
            
            if spec:
                new_word += spec[0]
            new_text.append(new_word)
        
        return new_text

    def train(self, num_fakes=4):
        words = random.sample(list(self.training_zoomer_dict.keys()), num_fakes+1)
        word = words[0]
        meaning = self.training_zoomer_dict[word]
        fakes = []
        for i, w in enumerate(words):
            if i == 0:
                continue
            fakes.append(self.training_zoomer_dict[w])
    
        return word, meaning, fakes


inp = argv[-1].split()
state = inp[0]
text = inp[1:]

translator = ZoomerTranslator()
if state == 'translate':
    translation = translator.boomer_to_zoomer(text)
    print(' '.join(translation))
else:
   word, answer, fakes = translator.train()
   print('{}|{}|{}|{}|{}|{}'.format(word, answer, fakes[0], fakes[1], fakes[2], fakes[3]))

