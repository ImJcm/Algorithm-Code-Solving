package Lv2;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;

/*
[3차] 방금그곡
제출 내역
문제 설명
방금그곡
라디오를 자주 듣는 네오는 라디오에서 방금 나왔던 음악이 무슨 음악인지 궁금해질 때가 많다. 그럴 때 네오는 다음 포털의 '방금그곡' 서비스를 이용하곤 한다. 방금그곡에서는 TV, 라디오 등에서 나온 음악에 관해 제목 등의 정보를 제공하는 서비스이다.

네오는 자신이 기억한 멜로디를 가지고 방금그곡을 이용해 음악을 찾는다. 그런데 라디오 방송에서는 한 음악을 반복해서 재생할 때도 있어서 네오가 기억하고 있는 멜로디는 음악 끝부분과 처음 부분이 이어서 재생된 멜로디일 수도 있다.
반대로, 한 음악을 중간에 끊을 경우 원본 음악에는 네오가 기억한 멜로디가 들어있다 해도 그 곡이 네오가 들은 곡이 아닐 수도 있다. 그렇기 때문에 네오는 기억한 멜로디를 재생 시간과 제공된 악보를 직접 보면서 비교하려고 한다. 다음과 같은 가정을 할 때 네오가 찾으려는 음악의 제목을 구하여라.

방금그곡 서비스에서는 음악 제목, 재생이 시작되고 끝난 시각, 악보를 제공한다.
네오가 기억한 멜로디와 악보에 사용되는 음은 C, C#, D, D#, E, F, F#, G, G#, A, A#, B 12개이다.
각 음은 1분에 1개씩 재생된다. 음악은 반드시 처음부터 재생되며 음악 길이보다 재생된 시간이 길 때는 음악이 끊김 없이 처음부터 반복해서 재생된다. 음악 길이보다 재생된 시간이 짧을 때는 처음부터 재생 시간만큼만 재생된다.
음악이 00:00를 넘겨서까지 재생되는 일은 없다.
조건이 일치하는 음악이 여러 개일 때에는 라디오에서 재생된 시간이 제일 긴 음악 제목을 반환한다. 재생된 시간도 같을 경우 먼저 입력된 음악 제목을 반환한다.
조건이 일치하는 음악이 없을 때에는 “(None)”을 반환한다.
입력 형식
입력으로 네오가 기억한 멜로디를 담은 문자열 m과 방송된 곡의 정보를 담고 있는 배열 musicinfos가 주어진다.

m은 음 1개 이상 1439개 이하로 구성되어 있다.
musicinfos는 100개 이하의 곡 정보를 담고 있는 배열로, 각각의 곡 정보는 음악이 시작한 시각, 끝난 시각, 음악 제목, 악보 정보가 ','로 구분된 문자열이다.
음악의 시작 시각과 끝난 시각은 24시간 HH:MM 형식이다.
음악 제목은 ',' 이외의 출력 가능한 문자로 표현된 길이 1 이상 64 이하의 문자열이다.
악보 정보는 음 1개 이상 1439개 이하로 구성되어 있다.
출력 형식
조건과 일치하는 음악 제목을 출력한다.

입출력 예시
m	musicinfos	answer
"ABCDEFG"	["12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"]	"HELLO"
"CC#BCC#BCC#BCC#B"	["03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"]	"FOO"
"ABC"	["12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"]	"WORLD"
설명
첫 번째 예시에서 HELLO는 길이가 7분이지만 12:00부터 12:14까지 재생되었으므로 실제로 CDEFGABCDEFGAB로 재생되었고, 이 중에 기억한 멜로디인 ABCDEFG가 들어있다.
세 번째 예시에서 HELLO는 C#DEFGABC#DEFGAB로, WORLD는 ABCDE로 재생되었다. HELLO 안에 있는 ABC#은 기억한 멜로디인 ABC와 일치하지 않고, WORLD 안에 있는 ABC가 기억한 멜로디와 일치한다.

해설 보러가기



※ 공지 - 2024년 2월 21일 테스트 케이스가 추가되었습니다. 기존에 제출한 코드가 통과하지 못할 수도 있습니다.
 */
/*
알고리즘 핵심
구현 (정렬 + Map)
1. 입력으로 주어진 곡의 정보를 시작시간, 끝나는시간, 곡명, 악보를 재구성하여 새로운 객체를 만든다.
악보를 재구성 : C# -> c 와 같이 C#, D#, ... 과 같음 음계를 새롭게 표현한다.
2. 재생시간이 긴 순서로 정렬하여 재생시간만큼 재생할 수 있는 악보를 만들어 입력으로 주어진 m을 포함하는지 확인한다.

이 문제는 입력으로 주어진 문제의 양식을 변경하여 구현하는 것과 HH:MM 포멧인 시간 문자열을 java.time에서 제공하는 라이브러리를 활용하는 점이 중요한 것 같다.
 */
public class _3차_방금그곡 {
    static void main() {
        String[] m = new String[] {
                //"ABCDEFG"
                "CC#BCC#BCC#BCC#B"
                //"ABC"

        };
        String[] musicinfos = new String[] {
                //"12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"
                "03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"
                //"12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"
        };

        Solve task = new Solve();
        System.out.println(task.solution(m[0], musicinfos));
    }

    private static class Solve {
        private class MusicInfo implements Comparable<MusicInfo> {
            String stime,etime,song_name,sheet_music;

            public MusicInfo(String stime, String etime, String song_name, String sheet_music) {
                this.stime = stime;
                this.etime = etime;
                this.song_name = song_name;
                this.sheet_music = sheet_music;
            }

            @Override
            public int compareTo(MusicInfo o) {
                return (int) (getDiffMinute(o.stime, o.etime) - getDiffMinute(this.stime, this.etime));
            }

            public long getDiffMinute(String t1, String t2) {
                LocalTime t1_time = LocalTime.parse(t1);
                LocalTime t2_time = LocalTime.parse(t2);
                return Duration.between(t1_time, t2_time).toMinutes();
            }
        }
        private String ans;
        private MusicInfo[] musicInfos;
        private HashMap<String, String> music_scales; //,de_music_scales;

        public String solution(String m, String[] musicinfos) {
            init_setting(m, musicinfos);

            that_song_just_now(m, musicInfos);

            return ans;
        }

        private void that_song_just_now(String m, MusicInfo[] musicInfos) {
            String tm = transform_sheet_music(m);

            for(int i = 0; i < musicInfos.length; i++) {
                MusicInfo mi = musicInfos[i];
                int diff_time = (int) mi.getDiffMinute(mi.stime, mi.etime);

                String play_music = mi.sheet_music.repeat(diff_time / mi.sheet_music.length()) + mi.sheet_music.substring(0, diff_time % mi.sheet_music.length());

                if(play_music.contains(tm)) {
                    ans = new String(mi.song_name);
                    break;
                }
            }
        }

        private void init_setting(String m, String[] musicinfos) {
            ans = new String("(None)");

            song_setting();

            musicInfos = new MusicInfo[musicinfos.length];

            for(int i = 0; i < musicinfos.length; i++) {
                String[] mi = musicinfos[i].split(",");

                String st = mi[0];
                String et = mi[1];
                String sn = mi[2];
                String ss = mi[3];

                ss = transform_sheet_music(ss);

                musicInfos[i] = new MusicInfo(st,et,sn,ss);
            }

            Arrays.sort(musicInfos);
        }

        private String transform_sheet_music(String ss) {
            StringBuilder sb = new StringBuilder();

            int i = 0;
            while(i < ss.length()) {
                char c1 = ss.charAt(i);
                char c2 = i + 1 >= ss.length() ? ' ' : ss.charAt(i + 1);

                if(c2 == '#') {
                    String s = music_scales.get(c1 + String.valueOf(c2));
                    sb.append(s);
                    i++;
                } else {
                    sb.append(c1);
                }
                i++;
            }
            return sb.toString();
        }

        /*private String retransform_sheet_music(String ss) {
            StringBuilder sb = new StringBuilder();

            int i = 0;
            while(i < ss.length()) {
                char c1 = ss.charAt(i);

                sb.append(de_music_scales.get(String.valueOf(c1)));
                i++;
            }
            return sb.toString();
        }*/

        private void song_setting() {
            music_scales = new HashMap<>();
            //de_music_scales = new HashMap<>();

            music_scales.put("A", "A");     //de_music_scales.put("A", "A");
            music_scales.put("A#", "a");    //de_music_scales.put("a", "A#");
            music_scales.put("B", "B");     //de_music_scales.put("B", "B");
            music_scales.put("C", "C");     //de_music_scales.put("C", "C");
            music_scales.put("C#", "c");    //de_music_scales.put("c", "C#");
            music_scales.put("D", "D");     //de_music_scales.put("D", "D");
            music_scales.put("D#", "d");    //de_music_scales.put("d", "D#");
            music_scales.put("E", "E");     //de_music_scales.put("E", "E");
            music_scales.put("F", "F");     //de_music_scales.put("F", "F");
            music_scales.put("F#", "f");    //de_music_scales.put("f", "F#");
            music_scales.put("G", "G");     //de_music_scales.put("G", "G");
            music_scales.put("G#", "g");    //de_music_scales.put("g", "G#");
        }
    }
}
