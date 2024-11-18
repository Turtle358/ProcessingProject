import os
import json
class DataLoader:
    def __init__(self):
        self.duration = []
        self.speed = []
        self.distance = []
        self.stepCount = []

    def loadData(self, dir: str):
        for item in os.listdir(dir):
            with open(dir+"/"+item, "r") as f:
                data = json.load(f)
                try:
                    self.stepCount.append(data["aggregate"][2]['intValue'])
                except KeyError:
                    continue
                try:
                    self.speed.append(data["aggregate"][4]['floatValue'])
                except KeyError:
                    continue
                self.duration.append(float(data["duration"][:-1]))
                self.distance.append(data["aggregate"][3]['floatValue'])



DataLoader().loadData("./All sessions")