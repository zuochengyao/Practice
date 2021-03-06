import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';
import 'package:CheeroFlutter/widget/scaffold/random_words_scaffold.dart';

import 'appbar/random_words_app_bar.dart';

class RandomWords extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new RandomWordsState();
}

class RandomWordsState extends State<RandomWords> {
  // 在Dart语言中使用下划线 _ 前缀标识符，会强制其变成私有的
  final _suggestions = <WordPair>[];
  final _biggerFont = const TextStyle(fontSize: 18.0);
  final _saved = new Set<WordPair>();

  @override
  Widget build(BuildContext context) {
    // final wordPair = new WordPair.random();
    // return new Text(wordPair.asPascalCase);
    return new RandomWordsScaffold(
      rwsAppBar: new RandomWordsAppBar(
          mContext: context,
          mTitle: 'Hello Cheero Flutter',
          mCenterTitle: true,
          mSaved: _saved),
      rwsBody: _buildSuggestions(),
    );
  }

  /// 构建显示建议单词对的ListView
  Widget _buildSuggestions() {
    return new ListView.builder(
        padding: const EdgeInsets.all(16.0),
        // 对于每个建议的单词对都会调用一次itemBuilder，然后将单词对添加到ListTile行中
        // 在偶数行，该函数会为单词对添加一个ListTile row.
        // 在奇数行，该函数会添加一个分割线widget，来分隔相邻的词对。
        // 注意，在小屏幕上，分割线看起来可能比较吃力。
        itemBuilder: (context, i) {
          if (i.isOdd) return new Divider();
          // 语法 "i ~/ 2" 表示i除以2，但返回值是整形（向下取整）
          // 比如i为：1, 2, 3, 4, 5时，结果为0, 1, 1, 2, 2
          // 这可以计算出ListView中减去分隔线后的实际单词对数量
          final index = i ~/ 2;
          // 如果是建议列表中最后一个单词对
          if (index >= _suggestions.length) {
            // ...接着再生成10个单词对，然后添加到建议列表
            _suggestions.addAll(generateWordPairs().take(10));
          }
          return _buildRow(_suggestions[index]);
        });
  }

  /// 这个函数在ListTile中显示每个新词对，这使您在下一步中可以生成更漂亮的显示行
  Widget _buildRow(WordPair pair) {
    final alreadySaved = _saved.contains(pair);
    return new ListTile(
      leading: new Icon(
        Icons.map,
        color: Colors.blue,
      ),
      title: new Text(pair.asPascalCase, style: _biggerFont),
      trailing: new Icon(
        alreadySaved ? Icons.favorite : Icons.favorite_border,
        color: alreadySaved ? Colors.red : null,
      ),
      onTap: () {
        setState(() {
          if (alreadySaved) {
            _saved.remove(pair);
          } else
            _saved.add(pair);
        });
      },
    );
  }
}
